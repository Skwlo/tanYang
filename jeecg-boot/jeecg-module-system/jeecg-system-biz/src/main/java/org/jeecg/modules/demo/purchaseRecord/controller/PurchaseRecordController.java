package org.jeecg.modules.demo.purchaseRecord.controller;

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
import org.jeecg.modules.demo.purchaseRecord.entity.PurchaseRecord;
import org.jeecg.modules.demo.purchaseRecord.service.IPurchaseRecordService;

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
 * @Description: 采购记录表
 * @Author: jeecg-boot
 * @Date:   2025-06-13
 * @Version: V1.0
 */
@Tag(name="采购记录表")
@RestController
@RequestMapping("/purchaseRecord/purchaseRecord")
@Slf4j
public class PurchaseRecordController extends JeecgController<PurchaseRecord, IPurchaseRecordService> {
	@Autowired
	private IPurchaseRecordService purchaseRecordService;
	
	/**
	 * 分页列表查询
	 *
	 * @param purchaseRecord
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "采购记录表-分页列表查询")
	@Operation(summary="采购记录表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<PurchaseRecord>> queryPageList(PurchaseRecord purchaseRecord,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<PurchaseRecord> queryWrapper = QueryGenerator.initQueryWrapper(purchaseRecord, req.getParameterMap());
		Page<PurchaseRecord> page = new Page<PurchaseRecord>(pageNo, pageSize);
		IPage<PurchaseRecord> pageList = purchaseRecordService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param purchaseRecord
	 * @return
	 */
	@AutoLog(value = "采购记录表-添加")
	@Operation(summary="采购记录表-添加")
	@RequiresPermissions("purchaseRecord:purchase_record:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody PurchaseRecord purchaseRecord) {
		purchaseRecordService.save(purchaseRecord);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param purchaseRecord
	 * @return
	 */
	@AutoLog(value = "采购记录表-编辑")
	@Operation(summary="采购记录表-编辑")
	@RequiresPermissions("purchaseRecord:purchase_record:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody PurchaseRecord purchaseRecord) {
		purchaseRecordService.updateById(purchaseRecord);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "采购记录表-通过id删除")
	@Operation(summary="采购记录表-通过id删除")
	@RequiresPermissions("purchaseRecord:purchase_record:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		purchaseRecordService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "采购记录表-批量删除")
	@Operation(summary="采购记录表-批量删除")
	@RequiresPermissions("purchaseRecord:purchase_record:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.purchaseRecordService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "采购记录表-通过id查询")
	@Operation(summary="采购记录表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<PurchaseRecord> queryById(@RequestParam(name="id",required=true) String id) {
		PurchaseRecord purchaseRecord = purchaseRecordService.getById(id);
		if(purchaseRecord==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(purchaseRecord);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param purchaseRecord
    */
    @RequiresPermissions("purchaseRecord:purchase_record:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, PurchaseRecord purchaseRecord) {
        return super.exportXls(request, purchaseRecord, PurchaseRecord.class, "采购记录表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("purchaseRecord:purchase_record:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, PurchaseRecord.class);
    }

}
