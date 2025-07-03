package org.jeecg.modules.demo.diseaseTreatmentRecord.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.math.BigDecimal;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.query.QueryRuleEnum;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.diseaseTreatmentRecord.entity.DiseaseTreatmentRecord;
import org.jeecg.modules.demo.diseaseTreatmentRecord.service.IDiseaseTreatmentRecordService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.demo.inventory.entity.Inventory;
import org.jeecg.modules.demo.inventory.service.IInventoryService;
import org.jeecg.modules.demo.stockOut.entity.StockOut;
import org.jeecg.modules.demo.stockOut.service.IStockOutService;
import org.jeecg.modules.demo.treatmentPlanMedication.entity.TreatmentPlanMedication;
import org.jeecg.modules.demo.treatmentPlanMedication.service.ITreatmentPlanMedicationService;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

 /**
 * @Description: 疾病治疗记录表
 * @Author: jeecg-boot
 * @Date:   2025-06-13
 * @Version: V1.0
 */
@Tag(name="疾病治疗记录表")
@RestController
@RequestMapping("/diseaseTreatmentRecord/diseaseTreatmentRecord")
@Slf4j
public class DiseaseTreatmentRecordController extends JeecgController<DiseaseTreatmentRecord, IDiseaseTreatmentRecordService> {
	@Autowired
	private IDiseaseTreatmentRecordService diseaseTreatmentRecordService;
	@Autowired
	private ITreatmentPlanMedicationService treatmentPlanMedicationService;
	@Autowired
	private IInventoryService inventoryService;
	@Autowired
	private IStockOutService stockOutService;

	/**
	 * 分页列表查询
	 *
	 * @param diseaseTreatmentRecord
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "疾病治疗记录表-分页列表查询")
	@Operation(summary="疾病治疗记录表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<DiseaseTreatmentRecord>> queryPageList(DiseaseTreatmentRecord diseaseTreatmentRecord,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        // 自定义查询规则
        Map<String, QueryRuleEnum> customeRuleMap = new HashMap<>();
        // 自定义多选的查询规则为：LIKE_WITH_OR
        customeRuleMap.put("diseaseType", QueryRuleEnum.LIKE_WITH_OR);
        customeRuleMap.put("isCured", QueryRuleEnum.LIKE_WITH_OR);
        QueryWrapper<DiseaseTreatmentRecord> queryWrapper = QueryGenerator.initQueryWrapper(diseaseTreatmentRecord, req.getParameterMap(),customeRuleMap);
		Page<DiseaseTreatmentRecord> page = new Page<DiseaseTreatmentRecord>(pageNo, pageSize);
		IPage<DiseaseTreatmentRecord> pageList = diseaseTreatmentRecordService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param diseaseTreatmentRecord
	 * @return
	 */
	@AutoLog(value = "疾病治疗记录表-添加")
	@Operation(summary="疾病治疗记录表-添加")
	@RequiresPermissions("diseaseTreatmentRecord:disease_treatment_record:add")
	@PostMapping("/add")
	@Transactional
	public Result<String> add(@RequestBody DiseaseTreatmentRecord diseaseTreatmentRecord) {
		// 获取当前用户名 作为出库操作时 记录操作人员名称
		Subject subject = SecurityUtils.getSubject();
		LoginUser loginUser = (LoginUser) subject.getPrincipal();
		String username = loginUser.getUsername();
		// 保存治疗记录
		diseaseTreatmentRecordService.save(diseaseTreatmentRecord);

		// 获取治疗方案 ID
		String treatmentPlanId = diseaseTreatmentRecord.getTreatmentPlanId();

		// 查询治疗方案对应的所有用药明细
		QueryWrapper<TreatmentPlanMedication> medicationQuery = new QueryWrapper<>();
		medicationQuery.eq("treatment_plan_id", treatmentPlanId);
		List<TreatmentPlanMedication> medicationList = treatmentPlanMedicationService.list(medicationQuery);

		if (medicationList == null || medicationList.isEmpty()) {
			return Result.error("治疗方案下无用药信息，无法扣减库存！");
		}

		for (TreatmentPlanMedication medication : medicationList) {
			String materialId = medication.getMaterialId(); // 药品 ID
			BigDecimal dosage = medication.getDosage();    // 每种药品用量

			// 根据 material_id 查询库存
			QueryWrapper<Inventory> inventoryQuery = new QueryWrapper<>();
			inventoryQuery.eq("material_id", materialId);
			Inventory inventory = inventoryService.getOne(inventoryQuery);

			if (inventory == null) {
				return Result.error("药品" + materialId + "库存不存在！");
			}

			if (inventory.getCurrentQuantity().compareTo(dosage) < 0) {
				return Result.error("药品" + materialId + "库存不足，无法完成治疗！");
			}

			// 扣减库存
			inventory.setCurrentQuantity(inventory.getCurrentQuantity().subtract(dosage));
			inventoryService.updateById(inventory);

			// 记录出库
			StockOut stockOut = new StockOut();
			stockOut.setMaterialId(materialId);
			stockOut.setQuantity(dosage);
			stockOut.setOutDate(diseaseTreatmentRecord.getOnsetDate());
			stockOut.setOperator(username);
			stockOut.setPurpose("疾病治疗");
			stockOut.setNote("治疗自动出库记录");
			stockOutService.save(stockOut);
		}

		return Result.OK("添加成功并同步扣减药品库存！");
	}



	 /**
	 *  编辑
	 *
	 * @param diseaseTreatmentRecord
	 * @return
	 */
	@AutoLog(value = "疾病治疗记录表-编辑")
	@Operation(summary="疾病治疗记录表-编辑")
	@RequiresPermissions("diseaseTreatmentRecord:disease_treatment_record:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody DiseaseTreatmentRecord diseaseTreatmentRecord) {
		diseaseTreatmentRecordService.updateById(diseaseTreatmentRecord);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "疾病治疗记录表-通过id删除")
	@Operation(summary="疾病治疗记录表-通过id删除")
	@RequiresPermissions("diseaseTreatmentRecord:disease_treatment_record:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		diseaseTreatmentRecordService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "疾病治疗记录表-批量删除")
	@Operation(summary="疾病治疗记录表-批量删除")
	@RequiresPermissions("diseaseTreatmentRecord:disease_treatment_record:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.diseaseTreatmentRecordService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "疾病治疗记录表-通过id查询")
	@Operation(summary="疾病治疗记录表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<DiseaseTreatmentRecord> queryById(@RequestParam(name="id",required=true) String id) {
		DiseaseTreatmentRecord diseaseTreatmentRecord = diseaseTreatmentRecordService.getById(id);
		if(diseaseTreatmentRecord==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(diseaseTreatmentRecord);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param diseaseTreatmentRecord
    */
    @RequiresPermissions("diseaseTreatmentRecord:disease_treatment_record:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, DiseaseTreatmentRecord diseaseTreatmentRecord) {
        return super.exportXls(request, diseaseTreatmentRecord, DiseaseTreatmentRecord.class, "疾病治疗记录表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("diseaseTreatmentRecord:disease_treatment_record:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, DiseaseTreatmentRecord.class);
    }

}
