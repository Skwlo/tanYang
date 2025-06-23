package org.jeecg.modules.demo.transferRecord.controller;

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
import org.jeecg.modules.demo.transferRecord.entity.TransferRecord;
import org.jeecg.modules.demo.transferRecord.service.ITransferRecordService;

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
 * @Description: 转群记录表
 * @Author: jeecg-boot
 * @Date:   2025-06-13
 * @Version: V1.0
 */
@Tag(name="转群记录表")
@RestController
@RequestMapping("/transferRecord/transferRecord")
@Slf4j
public class TransferRecordController extends JeecgController<TransferRecord, ITransferRecordService> {
	@Autowired
	private ITransferRecordService transferRecordService;
	
	/**
	 * 分页列表查询
	 *
	 * @param transferRecord
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "转群记录表-分页列表查询")
	@Operation(summary="转群记录表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<TransferRecord>> queryPageList(TransferRecord transferRecord,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<TransferRecord> queryWrapper = QueryGenerator.initQueryWrapper(transferRecord, req.getParameterMap());
		Page<TransferRecord> page = new Page<TransferRecord>(pageNo, pageSize);
		IPage<TransferRecord> pageList = transferRecordService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param transferRecord
	 * @return
	 */
	@AutoLog(value = "转群记录表-添加")
	@Operation(summary="转群记录表-添加")
	@RequiresPermissions("transferRecord:transfer_record:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody TransferRecord transferRecord) {
		transferRecordService.save(transferRecord);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param transferRecord
	 * @return
	 */
	@AutoLog(value = "转群记录表-编辑")
	@Operation(summary="转群记录表-编辑")
	@RequiresPermissions("transferRecord:transfer_record:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody TransferRecord transferRecord) {
		transferRecordService.updateById(transferRecord);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "转群记录表-通过id删除")
	@Operation(summary="转群记录表-通过id删除")
	@RequiresPermissions("transferRecord:transfer_record:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		transferRecordService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "转群记录表-批量删除")
	@Operation(summary="转群记录表-批量删除")
	@RequiresPermissions("transferRecord:transfer_record:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.transferRecordService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "转群记录表-通过id查询")
	@Operation(summary="转群记录表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<TransferRecord> queryById(@RequestParam(name="id",required=true) String id) {
		TransferRecord transferRecord = transferRecordService.getById(id);
		if(transferRecord==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(transferRecord);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param transferRecord
    */
    @RequiresPermissions("transferRecord:transfer_record:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, TransferRecord transferRecord) {
        return super.exportXls(request, transferRecord, TransferRecord.class, "转群记录表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("transferRecord:transfer_record:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, TransferRecord.class);
    }

}
