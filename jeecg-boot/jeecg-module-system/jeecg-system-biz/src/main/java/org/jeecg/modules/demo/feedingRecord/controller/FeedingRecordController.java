package org.jeecg.modules.demo.feedingRecord.controller;

import java.math.BigDecimal;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.query.QueryRuleEnum;
import org.jeecg.modules.demo.feedingRecord.entity.FeedingRecord;
import org.jeecg.modules.demo.feedingRecord.service.IFeedingRecordService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.demo.formula.entity.Formula;
import org.jeecg.modules.demo.formula.service.IFormulaService;
import org.jeecg.modules.demo.formulaIngredient.entity.FormulaIngredient;
import org.jeecg.modules.demo.formulaIngredient.service.IFormulaIngredientService;
import org.jeecg.modules.demo.inventory.entity.Inventory;
import org.jeecg.modules.demo.inventory.service.IInventoryService;
import org.jeecg.modules.demo.shedPen.entity.ShedPen;
import org.jeecg.modules.demo.shedPen.service.IShedPenService;
import org.jeecg.modules.demo.stockOut.entity.StockOut;
import org.jeecg.modules.demo.stockOut.service.IStockOutService;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

 /**
 * @Description: 喂食记录表
 * @Author: jeecg-boot
 * @Date:   2025-06-13
 * @Version: V1.0
 */
@Tag(name="喂食记录表")
@RestController
@RequestMapping("/feedingRecord/feedingRecord")
@Slf4j
public class FeedingRecordController extends JeecgController<FeedingRecord, IFeedingRecordService> {
	@Autowired
	private IFeedingRecordService feedingRecordService;

	 @Autowired
	 private IStockOutService stockOutService;

	 @Autowired
	 private IInventoryService inventoryService;

	 @Autowired
	 private IShedPenService shedPenService;
	 @Autowired
	 private IFormulaIngredientService formulaIngredientService;

	/**
	 * 分页列表查询
	 *
	 * @param feedingRecord
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "喂食记录表-分页列表查询")
	@Operation(summary="喂食记录表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<FeedingRecord>> queryPageList(FeedingRecord feedingRecord,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        // 自定义查询规则
        Map<String, QueryRuleEnum> customeRuleMap = new HashMap<>();
        // 自定义多选的查询规则为：LIKE_WITH_OR
        customeRuleMap.put("feedType", QueryRuleEnum.LIKE_WITH_OR);
        customeRuleMap.put("operator", QueryRuleEnum.LIKE_WITH_OR);
        QueryWrapper<FeedingRecord> queryWrapper = QueryGenerator.initQueryWrapper(feedingRecord, req.getParameterMap(),customeRuleMap);
		Page<FeedingRecord> page = new Page<FeedingRecord>(pageNo, pageSize);
		IPage<FeedingRecord> pageList = feedingRecordService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param feedingRecord
	 * @return
	 */
	@AutoLog(value = "喂食记录表-添加")
	@Operation(summary="喂食记录表-添加")
	@RequiresPermissions("feedingRecord:feeding_record:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody FeedingRecord feedingRecord) {
		// 1. 保存喂食记录
		feedingRecordService.save (feedingRecord);

		// 校验喂食棚栏 ID 是否存在于 ShedPen 表的 shed_pen_id 字段
		QueryWrapper<ShedPen> shedPenQuery = new QueryWrapper<>();
		shedPenQuery.eq("shed_pen_id", feedingRecord.getShedPenId());
		if (!shedPenService.exists(shedPenQuery)) {
			throw new RuntimeException("喂食棚栏 ID 不存在，无法完成喂食记录添加");
		}

		// 校验使用配方 ID 是否存在于 FormulaIngredient 表的 formulaId 字段
		QueryWrapper<FormulaIngredient> formulaQuery = new QueryWrapper<>();
		formulaQuery.eq("material_id", feedingRecord.getFormulaId());
		if (!formulaIngredientService.exists(formulaQuery)) {
			throw new RuntimeException("使用配方 ID 不存在，无法完成喂食记录添加");
		}

		// 喂食数量是否为空
		if (feedingRecord.getQuantity() == null) {
			throw new RuntimeException("喂食数量不能为空");
		}

		// 喂食数量是否为负
		if (feedingRecord.getQuantity().compareTo(BigDecimal.ZERO) <= 0) {
			throw new RuntimeException("喂食数量必须大于0");
		}


		// 限制最大喂食量（示例：限制为不超过1000kg，可根据实际业务调整）
		BigDecimal maxQuantity = new BigDecimal("1000");
		if (feedingRecord.getQuantity().compareTo(maxQuantity) > 0) {
			throw new RuntimeException("喂食数量不能超过" + maxQuantity + "kg");
		}

// 2. 校验饲料库存 (使用 materialId 关联库存表)
		QueryWrapper<Inventory> inventoryQuery = new QueryWrapper<>();
		inventoryQuery.eq ("material_id", feedingRecord.getFormulaId ()); // 假设配方 ID 对应物资 ID
		Inventory inventory = inventoryService.getOne (inventoryQuery);
		if (inventory == null || inventory.getCurrentQuantity ().compareTo (feedingRecord.getQuantity ()) < 0) {
			throw new RuntimeException ("饲料库存不足，无法完成喂食记录添加");
		}
// 3. 创建出库记录
		StockOut stockOut = new StockOut ();
		stockOut.setMaterialId (feedingRecord.getFormulaId ()); // 假设配方 ID 对应物资 ID
		stockOut.setQuantity (feedingRecord.getQuantity ());
		stockOut.setOutDate (new Date());
		stockOut.setPurpose ("喂食");
		stockOut.setOperator (feedingRecord.getOperator ());
		stockOut.setNote ("喂食消耗");
		stockOutService.save (stockOut);
// 4. 更新库存
		inventory.setCurrentQuantity (inventory.getCurrentQuantity ().subtract (feedingRecord.getQuantity ()));
		inventory.setLastOutDate (new Date ());
		inventoryService.updateById (inventory);
		return Result.OK ("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param feedingRecord
	 * @return
	 */
	@AutoLog(value = "喂食记录表-编辑")
	@Operation(summary="喂食记录表-编辑")
	@RequiresPermissions("feedingRecord:feeding_record:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody FeedingRecord feedingRecord) {

		// 校验喂食棚栏 ID 是否存在于 ShedPen 表的 shed_pen_id 字段
		QueryWrapper<ShedPen> shedPenQuery = new QueryWrapper<>();
		shedPenQuery.eq("shed_pen_id", feedingRecord.getShedPenId());
		if (!shedPenService.exists(shedPenQuery)) {
			throw new RuntimeException("喂食棚栏 ID 不存在，无法完成喂食记录添加");
		}

		// 校验使用配方 ID 是否存在于 FormulaIngredient 表的 formulaId 字段
		QueryWrapper<FormulaIngredient> formulaQuery = new QueryWrapper<>();
		formulaQuery.eq("material_id", feedingRecord.getFormulaId());
		if (!formulaIngredientService.exists(formulaQuery)) {
			throw new RuntimeException("使用配方 ID 不存在，无法完成喂食记录添加");
		}

		// 喂食数量是否为空
		if (feedingRecord.getQuantity() == null) {
			throw new RuntimeException("喂食数量不能为空");
		}

		// 喂食数量是否为负
		if (feedingRecord.getQuantity().compareTo(BigDecimal.ZERO) <= 0) {
			throw new RuntimeException("喂食数量必须大于0");
		}


		// 限制最大喂食量（示例：限制为不超过1000kg，可根据实际业务调整）
		BigDecimal maxQuantity = new BigDecimal("1000");
		if (feedingRecord.getQuantity().compareTo(maxQuantity) > 0) {
			throw new RuntimeException("喂食数量不能超过" + maxQuantity + "kg");
		}

		// 1. 查询原记录
		FeedingRecord originalRecord = feedingRecordService.getById (feedingRecord.getId ());
		if (originalRecord == null) {
			return Result.error ("未找到对应喂食记录");
		}
// 2. 计算饲料用量差异
		BigDecimal amountDiff = feedingRecord.getQuantity ().subtract (originalRecord.getQuantity ());
// 3. 更新喂食记录
		feedingRecordService.updateById (feedingRecord);
// 4. 更新库存和出库记录
		if (!amountDiff.equals (BigDecimal.ZERO)) {
// 查询库存
			QueryWrapper<Inventory> inventoryQuery = new QueryWrapper<>();
			inventoryQuery.eq ("material_id", feedingRecord.getFormulaId ()); // 假设配方 ID 对应物资 ID
			Inventory inventory = inventoryService.getOne (inventoryQuery);
			if (inventory == null) {
				throw new RuntimeException ("未找到饲料库存记录，无法完成更新");
			}
// 检查库存是否足够 (仅当增加用量时校验)
			if (amountDiff.compareTo (BigDecimal.ZERO) > 0 &&
					inventory.getCurrentQuantity ().compareTo (amountDiff) < 0) {
				throw new RuntimeException ("饲料库存不足，无法完成喂食记录修改");
			}
// 更新库存
			inventory.setCurrentQuantity (inventory.getCurrentQuantity ().subtract (amountDiff));
			inventory.setLastOutDate (new Date ());
			inventoryService.updateById (inventory);
// 更新出库记录
			QueryWrapper<StockOut> stockOutQuery = new QueryWrapper<>();
			stockOutQuery.eq("material_id", feedingRecord.getFormulaId()); // 使用配方ID关联物资ID
			StockOut stockOut = stockOutService.getOne(stockOutQuery);
			if (stockOut != null) {
				stockOut.setQuantity(feedingRecord.getQuantity());
				stockOutService.updateById(stockOut);
			}
		}
		return Result.OK ("编辑成功！");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "喂食记录表-通过id删除")
	@Operation(summary="喂食记录表-通过id删除")
	@RequiresPermissions("feedingRecord:feeding_record:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		// 1. 查询原记录
		FeedingRecord feedingRecord = feedingRecordService.getById (id);
		if (feedingRecord == null) {
			return Result.error ("未找到对应喂食记录");
		}
// 2. 删除喂食记录
		feedingRecordService.removeById (id);
// 3. 恢复库存
		if (feedingRecord.getFormulaId () != null && feedingRecord.getQuantity () != null) {
			QueryWrapper<Inventory> inventoryQuery = new QueryWrapper<>();
			inventoryQuery.eq ("material_id", feedingRecord.getFormulaId ()); // 假设配方 ID 对应物资 ID
			Inventory inventory = inventoryService.getOne (inventoryQuery);
			if (inventory != null) {
				inventory.setCurrentQuantity(inventory.getCurrentQuantity().add(feedingRecord.getQuantity()));
				inventoryService.updateById(inventory);
			}
// 4. 删除出库记录
			QueryWrapper<StockOut> stockOutQuery = new QueryWrapper<>();
			stockOutQuery.eq("material_id", feedingRecord.getFormulaId()) // 使用配方ID关联物资ID
					.eq("out_date", feedingRecord.getFeedDate())           // 使用喂食日期
					.eq("purpose", "feeding");
			stockOutService.remove(stockOutQuery);
		}
		return Result.OK ("删除成功！");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "喂食记录表-批量删除")
	@Operation(summary="喂食记录表-批量删除")
	@RequiresPermissions("feedingRecord:feeding_record:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
	List<String> idList = Arrays.asList(ids.split(","));

	 // 1. 查询所有喂食记录
	 List<FeedingRecord> records = feedingRecordService.listByIds(idList);

	 // 2. 批量删除
    this.feedingRecordService.removeByIds(idList);

	 // 3. 恢复库存（按配方 ID 分组处理）
	 Map<String, BigDecimal> formulaAmountMap = new HashMap<>();
    for (FeedingRecord record : records) {
		 if (record.getFormulaId() != null && record.getQuantity() != null) {
			 formulaAmountMap.put(record.getFormulaId(),
					 formulaAmountMap.getOrDefault(record.getFormulaId(), BigDecimal.ZERO)
							 .add(record.getQuantity()));
		 }
	 }

	 // 4. 更新库存
    for (Map.Entry<String, BigDecimal> entry : formulaAmountMap.entrySet()) {
		 QueryWrapper<Inventory> inventoryQuery = new QueryWrapper<>();
		 inventoryQuery.eq("material_id", entry.getKey());
		 Inventory inventory = inventoryService.getOne(inventoryQuery);
		 if (inventory != null) {
			 inventory.setCurrentQuantity(inventory.getCurrentQuantity().add(entry.getValue()));
			 inventoryService.updateById(inventory);
		 }
	 }

	 // 5. 删除关联出库记录（修改此处查询逻辑）
    for (FeedingRecord record : records) {
		 if (record.getFormulaId() != null && record.getFeedDate() != null) {
			 QueryWrapper<StockOut> stockOutQuery = new QueryWrapper<>();
			 stockOutQuery.eq("material_id", record.getFormulaId())  // 使用配方ID关联物资ID
					 .eq("out_date", record.getFeedDate())      // 使用喂食日期
					 .eq("purpose", "feeding");                  // 用途为喂食
			 stockOutService.remove(stockOutQuery);
		 }
	 }

    return Result.OK("批量删除成功！");
 }

 /**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "喂食记录表-通过id查询")
	@Operation(summary="喂食记录表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<FeedingRecord> queryById(@RequestParam(name="id",required=true) String id) {
		FeedingRecord feedingRecord = feedingRecordService.getById(id);
		if(feedingRecord==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(feedingRecord);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param feedingRecord
    */
    @RequiresPermissions("feedingRecord:feeding_record:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, FeedingRecord feedingRecord) {
        return super.exportXls(request, feedingRecord, FeedingRecord.class, "喂食记录表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("feedingRecord:feeding_record:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, FeedingRecord.class);
    }

}
