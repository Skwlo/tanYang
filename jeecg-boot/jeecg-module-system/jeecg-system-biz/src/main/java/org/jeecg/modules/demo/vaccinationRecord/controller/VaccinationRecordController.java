package org.jeecg.modules.demo.vaccinationRecord.controller;

import java.math.BigDecimal;
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

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.query.QueryRuleEnum;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.inventory.entity.Inventory;
import org.jeecg.modules.demo.inventory.service.IInventoryService;
import org.jeecg.modules.demo.stockOut.entity.StockOut;
import org.jeecg.modules.demo.stockOut.service.IStockOutService;
import org.jeecg.modules.demo.vaccinationRecord.entity.VaccinationRecord;
import org.jeecg.modules.demo.vaccinationRecord.service.IVaccinationRecordService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

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
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.jeecg.common.system.vo.LoginUser;

 /**
 * @Description: 免疫记录表
 * @Author: jeecg-boot
 * @Date:   2025-06-13
 * @Version: V1.0
 */
@Tag(name="免疫记录表")
@RestController
@RequestMapping("/vaccinationRecord/vaccinationRecord")
@Slf4j
public class VaccinationRecordController extends JeecgController<VaccinationRecord, IVaccinationRecordService> {
	@Autowired
	private IVaccinationRecordService vaccinationRecordService;
	@Autowired
	private IInventoryService inventoryService;

	@Autowired
	private IStockOutService stockOutService;

	/**
	 * 分页列表查询
	 *
	 * @param vaccinationRecord
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "免疫记录表-分页列表查询")
	@Operation(summary="免疫记录表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<VaccinationRecord>> queryPageList(VaccinationRecord vaccinationRecord,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        // 自定义查询规则
        Map<String, QueryRuleEnum> customeRuleMap = new HashMap<>();
        // 自定义多选的查询规则为：LIKE_WITH_OR
        customeRuleMap.put("scope", QueryRuleEnum.LIKE_WITH_OR);
        QueryWrapper<VaccinationRecord> queryWrapper = QueryGenerator.initQueryWrapper(vaccinationRecord, req.getParameterMap(),customeRuleMap);
		Page<VaccinationRecord> page = new Page<VaccinationRecord>(pageNo, pageSize);
		IPage<VaccinationRecord> pageList = vaccinationRecordService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param vaccinationRecord
	 * @return
	 */
//	@AutoLog(value = "免疫记录表-添加")
//	@Operation(summary="免疫记录表-添加")
//	@RequiresPermissions("vaccinationRecord:vaccination_record:add")
//	@PostMapping(value = "/add")
//	public Result<String> add(@RequestBody VaccinationRecord vaccinationRecord) {
//		vaccinationRecordService.save(vaccinationRecord);
//		return Result.OK("添加成功！");
//	}
	@PostMapping(value = "/add")
	@Transactional
	public Result<String> add(@RequestBody VaccinationRecord vaccinationRecord) {
		// 获取当前用户名 作为出库操作时 记录操作人员名称
		Subject subject = SecurityUtils.getSubject();
		LoginUser loginUser = (LoginUser) subject.getPrincipal();
		String username = loginUser.getUsername();

		// 判断疫苗在不在库存
		QueryWrapper<Inventory> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("material_id", vaccinationRecord.getVaccineId());
		Inventory inventory = inventoryService.getOne(queryWrapper);
		if (inventory == null) {
			return Result.error("库存信息不存在！");
		}

		// 判断疫苗库存数量够不够
		int usageQuantity = vaccinationRecord.getDosage().intValue();
		if (inventory.getCurrentQuantity().compareTo(BigDecimal.valueOf(usageQuantity)) < 0) {
			return Result.error("库存不足，无法完成免疫操作！");
		}

		// 保存免疫记录
		vaccinationRecordService.save(vaccinationRecord);

		// 在库存表减少疫苗数量
		inventory.setCurrentQuantity(inventory.getCurrentQuantity().subtract(BigDecimal.valueOf(usageQuantity)));
		inventory.setLastOutDate(vaccinationRecord.getVaccinationDate());
		inventoryService.updateById(inventory);

		// 在出库记录表中记录出库信息
		StockOut stockOut = new StockOut();
		stockOut.setMaterialId(vaccinationRecord.getVaccineId());
		stockOut.setQuantity(vaccinationRecord.getDosage());
		stockOut.setOutDate(vaccinationRecord.getVaccinationDate());
		stockOut.setPurpose("免疫");
		stockOut.setOperator(username);
		stockOut.setNote("免疫自动出库记录");
		stockOutService.save(stockOut);

		return Result.OK("添加成功");
	}


	/**
	 *  编辑
	 *
	 * @param vaccinationRecord
	 * @return
	 */
	@AutoLog(value = "免疫记录表-编辑")
	@Operation(summary="免疫记录表-编辑")
	@RequiresPermissions("vaccinationRecord:vaccination_record:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody VaccinationRecord vaccinationRecord) {
		vaccinationRecordService.updateById(vaccinationRecord);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "免疫记录表-通过id删除")
	@Operation(summary="免疫记录表-通过id删除")
	@RequiresPermissions("vaccinationRecord:vaccination_record:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		vaccinationRecordService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "免疫记录表-批量删除")
	@Operation(summary="免疫记录表-批量删除")
	@RequiresPermissions("vaccinationRecord:vaccination_record:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.vaccinationRecordService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "免疫记录表-通过id查询")
	@Operation(summary="免疫记录表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<VaccinationRecord> queryById(@RequestParam(name="id",required=true) String id) {
		VaccinationRecord vaccinationRecord = vaccinationRecordService.getById(id);
		if(vaccinationRecord==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(vaccinationRecord);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param vaccinationRecord
    */
    @RequiresPermissions("vaccinationRecord:vaccination_record:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, VaccinationRecord vaccinationRecord) {
        return super.exportXls(request, vaccinationRecord, VaccinationRecord.class, "免疫记录表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("vaccinationRecord:vaccination_record:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, VaccinationRecord.class);
    }
}