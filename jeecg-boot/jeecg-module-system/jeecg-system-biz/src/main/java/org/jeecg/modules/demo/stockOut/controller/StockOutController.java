package org.jeecg.modules.demo.stockOut.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.query.QueryRuleEnum;
import org.jeecg.modules.demo.stockOut.entity.StockOut;
import org.jeecg.modules.demo.stockOut.service.IStockOutService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

 /**
 * @Description: 出库记录表
 * @Author: jeecg-boot
 * @Date:   2025-06-13
 * @Version: V1.0
 */
@Tag(name="出库记录表")
@RestController
@RequestMapping("/stockOut/stockOut")
@Slf4j
public class StockOutController extends JeecgController<StockOut, IStockOutService> {
	@Autowired
	private IStockOutService stockOutService;
	
	/**
	 * 分页列表查询
	 *
	 * @param stockOut
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "出库记录表-分页列表查询")
	@Operation(summary="出库记录表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<StockOut>> queryPageList(StockOut stockOut,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        // 自定义查询规则
        Map<String, QueryRuleEnum> customeRuleMap = new HashMap<>();
        // 自定义多选的查询规则为：LIKE_WITH_OR
        customeRuleMap.put("purpose", QueryRuleEnum.LIKE_WITH_OR);
        QueryWrapper<StockOut> queryWrapper = QueryGenerator.initQueryWrapper(stockOut, req.getParameterMap(),customeRuleMap);
		Page<StockOut> page = new Page<StockOut>(pageNo, pageSize);
		IPage<StockOut> pageList = stockOutService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param stockOut
	 * @return
	 */
	@AutoLog(value = "出库记录表-添加")
	@Operation(summary="出库记录表-添加")
	@RequiresPermissions("stockOut:stock_out:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody StockOut stockOut) {
		try {
			stockOutService.handleStockOut(stockOut);
			return Result.OK("添加成功！");
		} catch (Exception e) {
			log.error("添加入库记录失败", e);
			return Result.error("添加入库记录失败: " + e.getMessage());
		}
	}
	
	/**
	 *  编辑
	 *
	 * @param stockOut
	 * @return
	 */
	@AutoLog(value = "出库记录表-编辑")
	@Operation(summary="出库记录表-编辑")
	@RequiresPermissions("stockOut:stock_out:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody StockOut stockOut) {
		stockOutService.updateById(stockOut);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "出库记录表-通过id删除")
	@Operation(summary="出库记录表-通过id删除")
	@RequiresPermissions("stockOut:stock_out:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		stockOutService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "出库记录表-批量删除")
	@Operation(summary="出库记录表-批量删除")
	@RequiresPermissions("stockOut:stock_out:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.stockOutService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "出库记录表-通过id查询")
	@Operation(summary="出库记录表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<StockOut> queryById(@RequestParam(name="id",required=true) String id) {
		StockOut stockOut = stockOutService.getById(id);
		if(stockOut==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(stockOut);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param stockOut
    */
    @RequiresPermissions("stockOut:stock_out:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, StockOut stockOut) {
        return super.exportXls(request, stockOut, StockOut.class, "出库记录表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("stockOut:stock_out:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, StockOut.class);
    }

}
