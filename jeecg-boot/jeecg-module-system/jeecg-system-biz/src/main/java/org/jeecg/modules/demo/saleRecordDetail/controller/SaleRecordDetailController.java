package org.jeecg.modules.demo.saleRecordDetail.controller;

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
import org.jeecg.modules.demo.saleRecordDetail.entity.SaleRecordDetail;
import org.jeecg.modules.demo.saleRecordDetail.service.ISaleRecordDetailService;

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
 * @Description: 销售明细表
 * @Author: jeecg-boot
 * @Date:   2025-06-13
 * @Version: V1.0
 */
@Tag(name="销售明细表")
@RestController
@RequestMapping("/saleRecordDetail/saleRecordDetail")
@Slf4j
public class SaleRecordDetailController extends JeecgController<SaleRecordDetail, ISaleRecordDetailService> {
	@Autowired
	private ISaleRecordDetailService saleRecordDetailService;
	
	/**
	 * 分页列表查询
	 *
	 * @param saleRecordDetail
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "销售明细表-分页列表查询")
	@Operation(summary="销售明细表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<SaleRecordDetail>> queryPageList(SaleRecordDetail saleRecordDetail,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<SaleRecordDetail> queryWrapper = QueryGenerator.initQueryWrapper(saleRecordDetail, req.getParameterMap());
		Page<SaleRecordDetail> page = new Page<SaleRecordDetail>(pageNo, pageSize);
		IPage<SaleRecordDetail> pageList = saleRecordDetailService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param saleRecordDetail
	 * @return
	 */
	@AutoLog(value = "销售明细表-添加")
	@Operation(summary="销售明细表-添加")
	@RequiresPermissions("saleRecordDetail:sale_record_detail:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody SaleRecordDetail saleRecordDetail) {
		try {
			saleRecordDetailService.changeStatus(saleRecordDetail);
			return Result.OK("添加成功！");
		} catch (Exception e) {
			return Result.error("添加失败"+ e.getMessage());
		}
	}
	
	/**
	 *  编辑
	 *
	 * @param saleRecordDetail
	 * @return
	 */
	@AutoLog(value = "销售明细表-编辑")
	@Operation(summary="销售明细表-编辑")
	@RequiresPermissions("saleRecordDetail:sale_record_detail:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody SaleRecordDetail saleRecordDetail) {
		saleRecordDetailService.updateById(saleRecordDetail);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "销售明细表-通过id删除")
	@Operation(summary="销售明细表-通过id删除")
	@RequiresPermissions("saleRecordDetail:sale_record_detail:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		saleRecordDetailService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "销售明细表-批量删除")
	@Operation(summary="销售明细表-批量删除")
	@RequiresPermissions("saleRecordDetail:sale_record_detail:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.saleRecordDetailService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "销售明细表-通过id查询")
	@Operation(summary="销售明细表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<SaleRecordDetail> queryById(@RequestParam(name="id",required=true) String id) {
		SaleRecordDetail saleRecordDetail = saleRecordDetailService.getById(id);
		if(saleRecordDetail==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(saleRecordDetail);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param saleRecordDetail
    */
    @RequiresPermissions("saleRecordDetail:sale_record_detail:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, SaleRecordDetail saleRecordDetail) {
        return super.exportXls(request, saleRecordDetail, SaleRecordDetail.class, "销售明细表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("saleRecordDetail:sale_record_detail:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, SaleRecordDetail.class);
    }

}
