package org.jeecg.modules.demo.livestock.controller;

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
import org.jeecg.modules.demo.livestock.entity.Livestock;
import org.jeecg.modules.demo.livestock.service.ILivestockService;

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
 * @Description: 畜只表
 * @Author: jeecg-boot
 * @Date:   2025-06-17
 * @Version: V1.0
 */
@Tag(name="畜只表")
@RestController
@RequestMapping("/livestock/livestock")
@Slf4j
public class LivestockController extends JeecgController<Livestock, ILivestockService> {
	@Autowired
	private ILivestockService livestockService;
	
	/**
	 * 分页列表查询
	 *
	 * @param livestock
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "畜只表-分页列表查询")
	@Operation(summary="畜只表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<Livestock>> queryPageList(Livestock livestock,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        // 自定义查询规则
        Map<String, QueryRuleEnum> customeRuleMap = new HashMap<>();
        // 自定义多选的查询规则为：LIKE_WITH_OR
        customeRuleMap.put("breed", QueryRuleEnum.LIKE_WITH_OR);
        customeRuleMap.put("category", QueryRuleEnum.LIKE_WITH_OR);
        customeRuleMap.put("gender", QueryRuleEnum.LIKE_WITH_OR);
        customeRuleMap.put("source", QueryRuleEnum.LIKE_WITH_OR);
        customeRuleMap.put("status", QueryRuleEnum.LIKE_WITH_OR);
        customeRuleMap.put("currentStage", QueryRuleEnum.LIKE_WITH_OR);
        QueryWrapper<Livestock> queryWrapper = QueryGenerator.initQueryWrapper(livestock, req.getParameterMap(),customeRuleMap);
		Page<Livestock> page = new Page<Livestock>(pageNo, pageSize);
		IPage<Livestock> pageList = livestockService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param livestock
	 * @return
	 */
	@AutoLog(value = "畜只表-添加")
	@Operation(summary="畜只表-添加")
	@RequiresPermissions("livestock:livestock:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody Livestock livestock) {
		livestockService.save(livestock);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param livestock
	 * @return
	 */
	@AutoLog(value = "畜只表-编辑")
	@Operation(summary="畜只表-编辑")
	@RequiresPermissions("livestock:livestock:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody Livestock livestock) {
		livestockService.updateById(livestock);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "畜只表-通过id删除")
	@Operation(summary="畜只表-通过id删除")
	@RequiresPermissions("livestock:livestock:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		livestockService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "畜只表-批量删除")
	@Operation(summary="畜只表-批量删除")
	@RequiresPermissions("livestock:livestock:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.livestockService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "畜只表-通过id查询")
	@Operation(summary="畜只表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<Livestock> queryById(@RequestParam(name="id",required=true) String id) {
		Livestock livestock = livestockService.getById(id);
		if(livestock==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(livestock);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param livestock
    */
    @RequiresPermissions("livestock:livestock:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Livestock livestock) {
        return super.exportXls(request, livestock, Livestock.class, "畜只表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("livestock:livestock:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, Livestock.class);
    }

}
