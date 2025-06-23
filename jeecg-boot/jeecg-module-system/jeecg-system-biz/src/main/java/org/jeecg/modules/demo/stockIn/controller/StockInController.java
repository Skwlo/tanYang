package org.jeecg.modules.demo.stockIn.controller;

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
import org.jeecg.modules.demo.stockIn.entity.StockIn;
import org.jeecg.modules.demo.stockIn.service.IStockInService;
import org.jeecg.modules.demo.inventory.entity.Inventory;
import org.jeecg.modules.demo.inventory.service.IInventoryService;

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
 * @Description: 入库记录表
 * @Author: jeecg-boot
 * @Date:   2025-06-13
 * @Version: V1.0
 */
@Tag(name="入库记录表")
@RestController
@RequestMapping("/stockIn/stockIn")
@Slf4j
public class StockInController extends JeecgController<StockIn, IStockInService> {
	@Autowired
	private IStockInService stockInService;
	
	/**
	 * 分页列表查询
	 *
	 * @param stockIn
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "入库记录表-分页列表查询")
	@Operation(summary="入库记录表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<StockIn>> queryPageList(StockIn stockIn,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<StockIn> queryWrapper = QueryGenerator.initQueryWrapper(stockIn, req.getParameterMap());
		Page<StockIn> page = new Page<StockIn>(pageNo, pageSize);
		IPage<StockIn> pageList = stockInService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param stockIn
	 * @return
	 */
	@AutoLog(value = "入库记录表-添加")
	@Operation(summary="入库记录表-添加")
	@RequiresPermissions("stockIn:stock_in:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody StockIn stockIn) {
		try {
			stockInService.handleStockIn(stockIn);
			return Result.OK("添加成功！");
		} catch (Exception e) {
			log.error("添加入库记录失败", e);
			return Result.error("添加入库记录失败: " + e.getMessage());
		}
	}
	
	/**
	 *  编辑
	 *
	 * @param stockIn
	 * @return
	 */
	@AutoLog(value = "入库记录表-编辑")
	@Operation(summary="入库记录表-编辑")
	@RequiresPermissions("stockIn:stock_in:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody StockIn stockIn) {
		stockInService.updateById(stockIn);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "入库记录表-通过id删除")
	@Operation(summary="入库记录表-通过id删除")
	@RequiresPermissions("stockIn:stock_in:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		stockInService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "入库记录表-批量删除")
	@Operation(summary="入库记录表-批量删除")
	@RequiresPermissions("stockIn:stock_in:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.stockInService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "入库记录表-通过id查询")
	@Operation(summary="入库记录表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<StockIn> queryById(@RequestParam(name="id",required=true) String id) {
		StockIn stockIn = stockInService.getById(id);
		if(stockIn==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(stockIn);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param stockIn
    */
    @RequiresPermissions("stockIn:stock_in:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, StockIn stockIn) {
        return super.exportXls(request, stockIn, StockIn.class, "入库记录表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("stockIn:stock_in:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, StockIn.class);
    }

}
