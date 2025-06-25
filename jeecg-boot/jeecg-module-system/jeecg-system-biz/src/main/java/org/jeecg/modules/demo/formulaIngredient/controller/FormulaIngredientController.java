package org.jeecg.modules.demo.formulaIngredient.controller;

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
import org.jeecg.modules.demo.formula.service.IFormulaService;
import org.jeecg.modules.demo.formulaIngredient.entity.FormulaIngredient;
import org.jeecg.modules.demo.formulaIngredient.service.IFormulaIngredientService;

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
 * @Description: 配方原料表
 * @Author: jeecg-boot
 * @Date:   2025-06-13
 * @Version: V1.0
 */
@Tag(name="配方原料表")
@RestController
@RequestMapping("/formulaIngredient/formulaIngredient")
@Slf4j
public class FormulaIngredientController extends JeecgController<FormulaIngredient, IFormulaIngredientService> {
	@Autowired
	private IFormulaIngredientService formulaIngredientService;

	@Autowired
	private IFormulaService formulaService;

	/**
	 * 分页列表查询
	 *
	 * @param formulaIngredient
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "配方原料表-分页列表查询")
	@Operation(summary="配方原料表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<FormulaIngredient>> queryPageList(FormulaIngredient formulaIngredient,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<FormulaIngredient> queryWrapper = QueryGenerator.initQueryWrapper(formulaIngredient, req.getParameterMap());
		Page<FormulaIngredient> page = new Page<FormulaIngredient>(pageNo, pageSize);
		IPage<FormulaIngredient> pageList = formulaIngredientService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param formulaIngredient
	 * @return
	 */
	@AutoLog(value = "配方原料表-添加")
	@Operation(summary="配方原料表-添加")
	@RequiresPermissions("formulaIngredient:formula_ingredient:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody FormulaIngredient formulaIngredient) {
		try {
			// 校验关联配方是否存在
			if (formulaService.getById(formulaIngredient.getFormulaId()) == null) {
				return Result.error("关联的配方不存在");
			}

			// 自动填充创建时间
			formulaIngredient.setCreateTime(new Date());

			formulaIngredientService.save(formulaIngredient);
			return Result.OK("添加成功！");
		} catch (Exception e) {
			log.error("添加配方原料失败: {}", e.getMessage(), e);
			return Result.error("添加失败，系统异常");
		}
	}

	/**
	 *  编辑
	 *
	 * @param formulaIngredient
	 * @return
	 */
	@AutoLog(value = "配方原料表-编辑")
	@Operation(summary="配方原料表-编辑")
	@RequiresPermissions("formulaIngredient:formula_ingredient:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody FormulaIngredient formulaIngredient) {
		try {
			// 校验记录是否存在
			FormulaIngredient oldIngredient = formulaIngredientService.getById(formulaIngredient.getId());
			if (oldIngredient == null) {
				return Result.error("配方原料记录不存在");
			}

			// 保留原始创建时间，更新修改时间
			formulaIngredient.setCreateTime(oldIngredient.getCreateTime());
			formulaIngredient.setUpdateTime(new Date());

			formulaIngredientService.updateById(formulaIngredient);
			return Result.OK("编辑成功!");
		} catch (Exception e) {
			log.error("编辑配方原料失败: {}", e.getMessage(), e);
			return Result.error("编辑失败，系统异常");
		}
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "配方原料表-通过id删除")
	@Operation(summary="配方原料表-通过id删除")
	@RequiresPermissions("formulaIngredient:formula_ingredient:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		try {
			// 校验原料记录是否存在
			FormulaIngredient ingredient = formulaIngredientService.getById(id);
			if (ingredient == null) {
				return Result.error("配方原料记录不存在");
			}

			// 执行删除
			formulaIngredientService.removeById(id);

			return Result.OK("删除成功!");
		} catch (Exception e) {
			log.error("删除配方原料失败: {}", e.getMessage(), e);
			return Result.error("删除失败，系统异常");
		}
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "配方原料表-批量删除")
	@Operation(summary="配方原料表-批量删除")
	@RequiresPermissions("formulaIngredient:formula_ingredient:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		try {
			List<String> idList = Arrays.asList(ids.split(","));

			// 查询所有要删除的原料，用于后续统计
			List<FormulaIngredient> ingredients = formulaIngredientService.listByIds(idList);

			// 执行批量删除
			boolean result = formulaIngredientService.removeByIds(idList);

			return result ? Result.OK("批量删除成功!") : Result.error("批量删除失败");
		} catch (Exception e) {
			log.error("批量删除配方原料失败: {}", e.getMessage(), e);
			return Result.error("批量删除失败，系统异常");
		}
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "配方原料表-通过id查询")
	@Operation(summary="配方原料表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<FormulaIngredient> queryById(@RequestParam(name="id",required=true) String id) {
		FormulaIngredient formulaIngredient = formulaIngredientService.getById(id);
		if(formulaIngredient==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(formulaIngredient);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param formulaIngredient
    */
    @RequiresPermissions("formulaIngredient:formula_ingredient:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, FormulaIngredient formulaIngredient) {
        return super.exportXls(request, formulaIngredient, FormulaIngredient.class, "配方原料表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("formulaIngredient:formula_ingredient:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, FormulaIngredient.class);
    }

}
