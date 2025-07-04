package org.jeecg.modules.demo.inventory.controller;

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
 * @Description: 库存表
 * @Author: jeecg-boot
 * @Date:   2025-06-17
 * @Version: V1.0
 */
@Tag(name="库存表")
@RestController
@RequestMapping("/inventory/inventory")
@Slf4j
public class InventoryController extends JeecgController<Inventory, IInventoryService> {
	@Autowired
	private IInventoryService inventoryService;
	
	/**
	 * 分页列表查询
	 *
	 * @param inventory
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "库存表-分页列表查询")
	@Operation(summary="库存表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<Inventory>> queryPageList(Inventory inventory,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<Inventory> queryWrapper = QueryGenerator.initQueryWrapper(inventory, req.getParameterMap());
		Page<Inventory> page = new Page<Inventory>(pageNo, pageSize);
		IPage<Inventory> pageList = inventoryService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param inventory
	 * @return
	 */
	@AutoLog(value = "库存表-添加")
	@Operation(summary="库存表-添加")
	@RequiresPermissions("inventory:inventory:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody Inventory inventory) {
		inventoryService.save(inventory);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param inventory
	 * @return
	 */
	@AutoLog(value = "库存表-编辑")
	@Operation(summary="库存表-编辑")
	@RequiresPermissions("inventory:inventory:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody Inventory inventory) {
		inventoryService.updateById(inventory);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "库存表-通过id删除")
	@Operation(summary="库存表-通过id删除")
	@RequiresPermissions("inventory:inventory:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		inventoryService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "库存表-批量删除")
	@Operation(summary="库存表-批量删除")
	@RequiresPermissions("inventory:inventory:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.inventoryService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "库存表-通过id查询")
	@Operation(summary="库存表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<Inventory> queryById(@RequestParam(name="id",required=true) String id) {
		Inventory inventory = inventoryService.getById(id);
		if(inventory==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(inventory);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param inventory
    */
    @RequiresPermissions("inventory:inventory:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Inventory inventory) {
        return super.exportXls(request, inventory, Inventory.class, "库存表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("inventory:inventory:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, Inventory.class);
    }

}
