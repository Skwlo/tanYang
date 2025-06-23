package org.jeecg.modules.demo.material.controller;

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
import org.jeecg.modules.demo.material.entity.Material;
import org.jeecg.modules.demo.material.service.IMaterialService;

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
 * @Description: 物资表
 * @Author: jeecg-boot
 * @Date:   2025-06-17
 * @Version: V1.0
 */
@Tag(name="物资表")
@RestController
@RequestMapping("/material/material")
@Slf4j
public class MaterialController extends JeecgController<Material, IMaterialService> {
	@Autowired
	private IMaterialService materialService;
	
	/**
	 * 分页列表查询
	 *
	 * @param material
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "物资表-分页列表查询")
	@Operation(summary="物资表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<Material>> queryPageList(Material material,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        // 自定义查询规则
        Map<String, QueryRuleEnum> customeRuleMap = new HashMap<>();
        // 自定义多选的查询规则为：LIKE_WITH_OR
        customeRuleMap.put("type", QueryRuleEnum.LIKE_WITH_OR);
        customeRuleMap.put("isMedicine", QueryRuleEnum.LIKE_WITH_OR);
        QueryWrapper<Material> queryWrapper = QueryGenerator.initQueryWrapper(material, req.getParameterMap(),customeRuleMap);
		Page<Material> page = new Page<Material>(pageNo, pageSize);
		IPage<Material> pageList = materialService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param material
	 * @return
	 */
	@AutoLog(value = "物资表-添加")
	@Operation(summary="物资表-添加")
	@RequiresPermissions("material:material:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody Material material) {
		materialService.save(material);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param material
	 * @return
	 */
	@AutoLog(value = "物资表-编辑")
	@Operation(summary="物资表-编辑")
	@RequiresPermissions("material:material:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody Material material) {
		materialService.updateById(material);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "物资表-通过id删除")
	@Operation(summary="物资表-通过id删除")
	@RequiresPermissions("material:material:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		materialService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "物资表-批量删除")
	@Operation(summary="物资表-批量删除")
	@RequiresPermissions("material:material:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.materialService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "物资表-通过id查询")
	@Operation(summary="物资表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<Material> queryById(@RequestParam(name="id",required=true) String id) {
		Material material = materialService.getById(id);
		if(material==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(material);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param material
    */
    @RequiresPermissions("material:material:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Material material) {
        return super.exportXls(request, material, Material.class, "物资表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("material:material:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, Material.class);
    }

}
