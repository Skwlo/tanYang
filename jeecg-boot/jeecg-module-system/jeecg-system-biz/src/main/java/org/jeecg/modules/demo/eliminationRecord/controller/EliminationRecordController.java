package org.jeecg.modules.demo.eliminationRecord.controller;

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
import org.jeecg.modules.demo.eliminationRecord.entity.EliminationRecord;
import org.jeecg.modules.demo.eliminationRecord.service.IEliminationRecordService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.demo.saleRecordDetail.entity.SaleRecordDetail;
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
 * @Description: 淘汰记录表
 * @Author: jeecg-boot
 * @Date:   2025-06-13
 * @Version: V1.0
 */
@Tag(name="淘汰记录表")
@RestController
@RequestMapping("/eliminationRecord/eliminationRecord")
@Slf4j
public class EliminationRecordController extends JeecgController<EliminationRecord, IEliminationRecordService> {
	@Autowired
	private IEliminationRecordService eliminationRecordService;
	
	/**
	 * 分页列表查询
	 *
	 * @param eliminationRecord
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "淘汰记录表-分页列表查询")
	@Operation(summary="淘汰记录表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<EliminationRecord>> queryPageList(EliminationRecord eliminationRecord,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        // 自定义查询规则
        Map<String, QueryRuleEnum> customeRuleMap = new HashMap<>();
        // 自定义多选的查询规则为：LIKE_WITH_OR
        customeRuleMap.put("beforeCategory", QueryRuleEnum.LIKE_WITH_OR);
        QueryWrapper<EliminationRecord> queryWrapper = QueryGenerator.initQueryWrapper(eliminationRecord, req.getParameterMap(),customeRuleMap);
		Page<EliminationRecord> page = new Page<EliminationRecord>(pageNo, pageSize);
		IPage<EliminationRecord> pageList = eliminationRecordService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param eliminationRecord
	 * @return
	 */
	@AutoLog(value = "淘汰记录表-添加")
	@Operation(summary="淘汰记录表-添加")
	@RequiresPermissions("eliminationRecord:elimination_record:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody EliminationRecord eliminationRecord) {
		try {
			eliminationRecordService.updstatus(eliminationRecord);
			return Result.OK("添加成功！");
		} catch (Exception e) {
			return Result.error("添加失败"+ e.getMessage());
		}
	}

	
	/**
	 *  编辑
	 *
	 * @param eliminationRecord
	 * @return
	 */
	@AutoLog(value = "淘汰记录表-编辑")
	@Operation(summary="淘汰记录表-编辑")
	@RequiresPermissions("eliminationRecord:elimination_record:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody EliminationRecord eliminationRecord) {
		eliminationRecordService.updateById(eliminationRecord);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "淘汰记录表-通过id删除")
	@Operation(summary="淘汰记录表-通过id删除")
	@RequiresPermissions("eliminationRecord:elimination_record:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		eliminationRecordService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "淘汰记录表-批量删除")
	@Operation(summary="淘汰记录表-批量删除")
	@RequiresPermissions("eliminationRecord:elimination_record:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.eliminationRecordService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "淘汰记录表-通过id查询")
	@Operation(summary="淘汰记录表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<EliminationRecord> queryById(@RequestParam(name="id",required=true) String id) {
		EliminationRecord eliminationRecord = eliminationRecordService.getById(id);
		if(eliminationRecord==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(eliminationRecord);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param eliminationRecord
    */
    @RequiresPermissions("eliminationRecord:elimination_record:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, EliminationRecord eliminationRecord) {
        return super.exportXls(request, eliminationRecord, EliminationRecord.class, "淘汰记录表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("eliminationRecord:elimination_record:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, EliminationRecord.class);
    }

}
