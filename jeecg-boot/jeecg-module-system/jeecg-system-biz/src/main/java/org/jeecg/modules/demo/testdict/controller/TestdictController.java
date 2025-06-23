package org.jeecg.modules.demo.testdict.controller;

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
import org.jeecg.modules.demo.testdict.entity.Testdict;
import org.jeecg.modules.demo.testdict.service.ITestdictService;

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
 * @Description: 字典
 * @Author: jeecg-boot
 * @Date:   2025-06-11
 * @Version: V1.0
 */
@Tag(name="字典")
@RestController
@RequestMapping("/testdict/testdict")
@Slf4j
public class TestdictController extends JeecgController<Testdict, ITestdictService> {
	@Autowired
	private ITestdictService testdictService;
	
	/**
	 * 分页列表查询
	 *
	 * @param testdict
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "字典-分页列表查询")
	@Operation(summary="字典-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<Testdict>> queryPageList(Testdict testdict,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<Testdict> queryWrapper = QueryGenerator.initQueryWrapper(testdict, req.getParameterMap());
		Page<Testdict> page = new Page<Testdict>(pageNo, pageSize);
		IPage<Testdict> pageList = testdictService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param testdict
	 * @return
	 */
	@AutoLog(value = "字典-添加")
	@Operation(summary="字典-添加")
	@RequiresPermissions("testdict:nmu_testdict:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody Testdict testdict) {
		testdictService.save(testdict);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param testdict
	 * @return
	 */
	@AutoLog(value = "字典-编辑")
	@Operation(summary="字典-编辑")
	@RequiresPermissions("testdict:nmu_testdict:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody Testdict testdict) {
		testdictService.updateById(testdict);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "字典-通过id删除")
	@Operation(summary="字典-通过id删除")
	@RequiresPermissions("testdict:nmu_testdict:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		testdictService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "字典-批量删除")
	@Operation(summary="字典-批量删除")
	@RequiresPermissions("testdict:nmu_testdict:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.testdictService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "字典-通过id查询")
	@Operation(summary="字典-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<Testdict> queryById(@RequestParam(name="id",required=true) String id) {
		Testdict testdict = testdictService.getById(id);
		if(testdict==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(testdict);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param testdict
    */
    @RequiresPermissions("testdict:nmu_testdict:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Testdict testdict) {
        return super.exportXls(request, testdict, Testdict.class, "字典");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("testdict:nmu_testdict:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, Testdict.class);
    }

}
