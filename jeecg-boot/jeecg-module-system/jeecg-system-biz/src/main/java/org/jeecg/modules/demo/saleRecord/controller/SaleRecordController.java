package org.jeecg.modules.demo.saleRecord.controller;

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
import org.jeecg.modules.demo.saleRecord.entity.SaleRecord;
import org.jeecg.modules.demo.saleRecord.service.ISaleRecordService;

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
 * @Description: 销售记录表
 * @Author: jeecg-boot
 * @Date:   2025-06-13
 * @Version: V1.0
 */
@Tag(name="销售记录表")
@RestController
@RequestMapping("/saleRecord/saleRecord")
@Slf4j
public class SaleRecordController extends JeecgController<SaleRecord, ISaleRecordService> {
	@Autowired
	private ISaleRecordService saleRecordService;
	
	/**
	 * 分页列表查询
	 *
	 * @param saleRecord
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "销售记录表-分页列表查询")
	@Operation(summary="销售记录表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<SaleRecord>> queryPageList(SaleRecord saleRecord,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<SaleRecord> queryWrapper = QueryGenerator.initQueryWrapper(saleRecord, req.getParameterMap());
		Page<SaleRecord> page = new Page<SaleRecord>(pageNo, pageSize);
		IPage<SaleRecord> pageList = saleRecordService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param saleRecord
	 * @return
	 */
	@AutoLog(value = "销售记录表-添加")
	@Operation(summary="销售记录表-添加")
	@RequiresPermissions("saleRecord:sale_record:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody SaleRecord saleRecord) {
		saleRecordService.save(saleRecord);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param saleRecord
	 * @return
	 */
	@AutoLog(value = "销售记录表-编辑")
	@Operation(summary="销售记录表-编辑")
	@RequiresPermissions("saleRecord:sale_record:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody SaleRecord saleRecord) {
		saleRecordService.updateById(saleRecord);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "销售记录表-通过id删除")
	@Operation(summary="销售记录表-通过id删除")
	@RequiresPermissions("saleRecord:sale_record:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		saleRecordService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "销售记录表-批量删除")
	@Operation(summary="销售记录表-批量删除")
	@RequiresPermissions("saleRecord:sale_record:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.saleRecordService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "销售记录表-通过id查询")
	@Operation(summary="销售记录表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<SaleRecord> queryById(@RequestParam(name="id",required=true) String id) {
		SaleRecord saleRecord = saleRecordService.getById(id);
		if(saleRecord==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(saleRecord);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param saleRecord
    */
    @RequiresPermissions("saleRecord:sale_record:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, SaleRecord saleRecord) {
        return super.exportXls(request, saleRecord, SaleRecord.class, "销售记录表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("saleRecord:sale_record:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, SaleRecord.class);
    }

}
