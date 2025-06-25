package org.jeecg.modules.demo.formula.controller;

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
import org.jeecg.modules.demo.formula.entity.Formula;
import org.jeecg.modules.demo.formula.service.IFormulaService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.demo.formulaIngredient.entity.FormulaIngredient;
import org.jeecg.modules.demo.formulaIngredient.service.IFormulaIngredientService;
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
 * @Description: 配方表
 * @Author: jeecg-boot
 * @Date:   2025-06-13
 * @Version: V1.0
 */
@Tag(name="配方表")
@RestController
@RequestMapping("/formula/formula")
@Slf4j
public class FormulaController extends JeecgController<Formula, IFormulaService> {
	@Autowired
	private IFormulaService formulaService;

	@Autowired
	private IFormulaIngredientService formulaIngredientService;

	/**
	 * 分页列表查询
	 *
	 * @param formula
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "配方表-分页列表查询")
	@Operation(summary="配方表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<Formula>> queryPageList(Formula formula,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        // 自定义查询规则
        Map<String, QueryRuleEnum> customeRuleMap = new HashMap<>();
        // 自定义多选的查询规则为：LIKE_WITH_OR
        customeRuleMap.put("applicableStage", QueryRuleEnum.LIKE_WITH_OR);
        QueryWrapper<Formula> queryWrapper = QueryGenerator.initQueryWrapper(formula, req.getParameterMap(),customeRuleMap);
		Page<Formula> page = new Page<Formula>(pageNo, pageSize);
		IPage<Formula> pageList = formulaService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param formula
	 * @return
	 */
	@AutoLog(value = "配方表-添加")
	@Operation(summary="配方表-添加")
	@RequiresPermissions("formula:formula:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody Formula formula) {
		try {
			// 校验配方名称唯一性
			if (formulaService.count(new QueryWrapper<Formula>().eq("name", formula.getName())) > 0) {
				return Result.error("配方名称已存在");
			}

			// 自动填充创建时间
			formula.setCreateTime(new Date());

			formulaService.save(formula);
			return Result.OK("添加成功！");
		} catch (Exception e) {
			log.error("添加配方失败: {}", e.getMessage(), e);
			return Result.error("添加失败，系统异常");
		}
	}

	/**
	 *  编辑
	 *
	 * @param formula
	 * @return
	 */
	@AutoLog(value = "配方表-编辑")
	@Operation(summary="配方表-编辑")
	@RequiresPermissions("formula:formula:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody Formula formula) {
		try {
			// 校验配方是否存在
			Formula oldFormula = formulaService.getById(formula.getId());
			if (oldFormula == null) {
				return Result.error("配方不存在");
			}

			// 若修改了配方名称，校验新名称唯一性
			if (!oldFormula.getName().equals(formula.getName()) &&
					formulaService.count(new QueryWrapper<Formula>().eq("name", formula.getName())) > 0) {
				return Result.error("新配方名称已存在");
			}

			// 保留原始创建信息，仅更新可修改字段
			formula.setCreateBy(oldFormula.getCreateBy());
			formula.setCreateTime(oldFormula.getCreateTime());
			formula.setUpdateTime(new Date()); // 更新修改时间

			formulaService.updateById(formula);
			return Result.OK("编辑成功!");
		} catch (Exception e) {
			log.error("编辑配方失败: {}", e.getMessage(), e);
			return Result.error("编辑失败，系统异常");
		}
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "配方表-通过id删除")
	@Operation(summary="配方表-通过id删除")
	@RequiresPermissions("formula:formula:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		try {
			// 级联删除：先删除关联原料，再删除配方
			formulaIngredientService.remove(new QueryWrapper<FormulaIngredient>().eq("formula_id", id));
			formulaService.removeById(id);
			return Result.OK("删除成功!");
		} catch (Exception e) {
			log.error("删除配方失败: {}", e.getMessage(), e);
			return Result.error("删除失败，系统异常");
		}
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "配方表-批量删除")
	@Operation(summary="配方表-批量删除")
	@RequiresPermissions("formula:formula:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		try {
			List<String> idList = Arrays.asList(ids.split(","));

			// 级联批量删除
			formulaIngredientService.remove(new QueryWrapper<FormulaIngredient>().in("formula_id", idList));
			formulaService.removeByIds(idList);

			return Result.OK("批量删除成功!");
		} catch (Exception e) {
			log.error("批量删除配方失败: {}", e.getMessage(), e);
			return Result.error("批量删除失败，系统异常");
		}
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "配方表-通过id查询")
	@Operation(summary="配方表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<Formula> queryById(@RequestParam(name="id",required=true) String id) {
		Formula formula = formulaService.getById(id);
		if(formula==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(formula);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param formula
    */
    @RequiresPermissions("formula:formula:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Formula formula) {
        return super.exportXls(request, formula, Formula.class, "配方表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("formula:formula:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, Formula.class);
    }

}
