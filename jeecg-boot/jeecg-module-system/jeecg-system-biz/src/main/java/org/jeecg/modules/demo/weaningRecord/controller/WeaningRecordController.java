package org.jeecg.modules.demo.weaningRecord.controller;

import java.time.LocalDate;
import java.util.*;
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
import org.jeecg.modules.demo.livestock.entity.Livestock;
import org.jeecg.modules.demo.livestock.service.ILivestockService;
import org.jeecg.modules.demo.weaningRecord.entity.WeaningRecord;
import org.jeecg.modules.demo.weaningRecord.service.IWeaningRecordService;

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
 * @Description: 断奶记录表
 * @Author: jeecg-boot
 * @Date:   2025-06-13
 * @Version: V1.0
 */
@Tag(name="断奶记录表")
@RestController
@RequestMapping("/weaningRecord/weaningRecord")
@Slf4j
public class WeaningRecordController extends JeecgController<WeaningRecord, IWeaningRecordService> {
	@Autowired
	private IWeaningRecordService weaningRecordService;
	@Autowired
	private ILivestockService livestockService;
	/**
	 * 分页列表查询
	 *
	 * @param weaningRecord
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "断奶记录表-分页列表查询")
	@Operation(summary="断奶记录表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<WeaningRecord>> queryPageList(WeaningRecord weaningRecord,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<WeaningRecord> queryWrapper = QueryGenerator.initQueryWrapper(weaningRecord, req.getParameterMap());
		Page<WeaningRecord> page = new Page<WeaningRecord>(pageNo, pageSize);
		IPage<WeaningRecord> pageList = weaningRecordService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param weaningRecord
	 * @return
	 */
	@AutoLog(value = "断奶记录表-添加")
	@Operation(summary="断奶记录表-添加")
	@RequiresPermissions("weaningRecord:weaning_record:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody WeaningRecord weaningRecord) {
		// 获取输入的档案ID和新棚舍ID
		String livestockId = weaningRecord.getLivestockId();
		String newShedPenId = weaningRecord.getNewShedPenId();
		// 将输入的档案ID与档案表中的ID进行匹配
		QueryWrapper<Livestock> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("livestock_id", livestockId);
		Livestock livestock = livestockService.getOne(queryWrapper);
		if(livestock == null){
			return Result.error("该畜只不存在");
		}else {
			// 计算断奶日龄 保留整数(天)
			Date birthdayDate = livestock.getBirthDate();
			Date weaningDateDate = weaningRecord.getWeaningDate();
			long diffMillis = weaningDateDate.getTime() - birthdayDate.getTime();
			int weaningAge = (int) (diffMillis / (1000 * 60 * 60 * 24));
			/*	储存断奶日龄
				并将档案表中当前牛的重量作为断奶记录中的重量
				同时设置新棚舍 最后更新档案表
			 */
			weaningRecord.setWeaningAge(weaningAge);
			weaningRecord.setWeaningWeight(livestock.getWeight());
			livestock.setCurrentShedPenId(newShedPenId);
			livestockService.updateById(livestock);
		}

		weaningRecordService.save(weaningRecord);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param weaningRecord
	 * @return
	 */
	@AutoLog(value = "断奶记录表-编辑")
	@Operation(summary="断奶记录表-编辑")
	@RequiresPermissions("weaningRecord:weaning_record:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody WeaningRecord weaningRecord) {
		weaningRecordService.updateById(weaningRecord);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "断奶记录表-通过id删除")
	@Operation(summary="断奶记录表-通过id删除")
	@RequiresPermissions("weaningRecord:weaning_record:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		weaningRecordService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "断奶记录表-批量删除")
	@Operation(summary="断奶记录表-批量删除")
	@RequiresPermissions("weaningRecord:weaning_record:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.weaningRecordService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "断奶记录表-通过id查询")
	@Operation(summary="断奶记录表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<WeaningRecord> queryById(@RequestParam(name="id",required=true) String id) {
		WeaningRecord weaningRecord = weaningRecordService.getById(id);
		if(weaningRecord==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(weaningRecord);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param weaningRecord
    */
    @RequiresPermissions("weaningRecord:weaning_record:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WeaningRecord weaningRecord) {
        return super.exportXls(request, weaningRecord, WeaningRecord.class, "断奶记录表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("weaningRecord:weaning_record:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, WeaningRecord.class);
    }

}
