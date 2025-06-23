package org.jeecg.modules.demo.feedingRecord.controller;

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
import org.jeecg.modules.demo.feedingRecord.entity.FeedingRecord;
import org.jeecg.modules.demo.feedingRecord.service.IFeedingRecordService;

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
		feedingRecordService.save(feedingRecord);
		return Result.OK("添加成功！");
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
		feedingRecordService.updateById(feedingRecord);
		return Result.OK("编辑成功!");
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
		feedingRecordService.removeById(id);
		return Result.OK("删除成功!");
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
		this.feedingRecordService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
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
