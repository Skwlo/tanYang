package org.jeecg.modules.demo.treatmentPlanMedication.controller;

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
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.query.QueryRuleEnum;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.treatmentPlanMedication.entity.TreatmentPlanMedication;
import org.jeecg.modules.demo.treatmentPlanMedication.service.ITreatmentPlanMedicationService;

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
import org.apache.shiro.authz.annotation.RequiresPermissions;

 /**
 * @Description: 治疗方案用药明细表
 * @Author: jeecg-boot
 * @Date:   2025-06-13
 * @Version: V1.0
 */
@Tag(name="治疗方案用药明细表")
@RestController
@RequestMapping("/treatmentPlanMedication/treatmentPlanMedication")
@Slf4j
public class TreatmentPlanMedicationController extends JeecgController<TreatmentPlanMedication, ITreatmentPlanMedicationService> {
	@Autowired
	private ITreatmentPlanMedicationService treatmentPlanMedicationService;
	
	/**
	 * 分页列表查询
	 *
	 * @param treatmentPlanMedication
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "治疗方案用药明细表-分页列表查询")
	@Operation(summary="治疗方案用药明细表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<TreatmentPlanMedication>> queryPageList(TreatmentPlanMedication treatmentPlanMedication,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<TreatmentPlanMedication> queryWrapper = QueryGenerator.initQueryWrapper(treatmentPlanMedication, req.getParameterMap());
		Page<TreatmentPlanMedication> page = new Page<TreatmentPlanMedication>(pageNo, pageSize);
		IPage<TreatmentPlanMedication> pageList = treatmentPlanMedicationService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param treatmentPlanMedication
	 * @return
	 */
	@AutoLog(value = "治疗方案用药明细表-添加")
	@Operation(summary="治疗方案用药明细表-添加")
	@RequiresPermissions("treatmentPlanMedication:treatment_plan_medication:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody TreatmentPlanMedication treatmentPlanMedication) {
		treatmentPlanMedicationService.save(treatmentPlanMedication);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param treatmentPlanMedication
	 * @return
	 */
	@AutoLog(value = "治疗方案用药明细表-编辑")
	@Operation(summary="治疗方案用药明细表-编辑")
	@RequiresPermissions("treatmentPlanMedication:treatment_plan_medication:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody TreatmentPlanMedication treatmentPlanMedication) {
		treatmentPlanMedicationService.updateById(treatmentPlanMedication);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "治疗方案用药明细表-通过id删除")
	@Operation(summary="治疗方案用药明细表-通过id删除")
	@RequiresPermissions("treatmentPlanMedication:treatment_plan_medication:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		treatmentPlanMedicationService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "治疗方案用药明细表-批量删除")
	@Operation(summary="治疗方案用药明细表-批量删除")
	@RequiresPermissions("treatmentPlanMedication:treatment_plan_medication:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.treatmentPlanMedicationService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "治疗方案用药明细表-通过id查询")
	@Operation(summary="治疗方案用药明细表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<TreatmentPlanMedication> queryById(@RequestParam(name="id",required=true) String id) {
		TreatmentPlanMedication treatmentPlanMedication = treatmentPlanMedicationService.getById(id);
		if(treatmentPlanMedication==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(treatmentPlanMedication);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param treatmentPlanMedication
    */
    @RequiresPermissions("treatmentPlanMedication:treatment_plan_medication:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, TreatmentPlanMedication treatmentPlanMedication) {
        return super.exportXls(request, treatmentPlanMedication, TreatmentPlanMedication.class, "治疗方案用药明细表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("treatmentPlanMedication:treatment_plan_medication:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, TreatmentPlanMedication.class);
    }

}
