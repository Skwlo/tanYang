package org.jeecg.modules.demo.dealer.controller;

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
import org.jeecg.modules.demo.dealer.entity.Dealer;
import org.jeecg.modules.demo.dealer.service.IDealerService;

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
 * @Description: 经销商表
 * @Author: jeecg-boot
 * @Date:   2025-06-17
 * @Version: V1.0
 */
@Tag(name="经销商表")
@RestController
@RequestMapping("/dealer/dealer")
@Slf4j
public class DealerController extends JeecgController<Dealer, IDealerService> {
	@Autowired
	private IDealerService dealerService;
	
	/**
	 * 分页列表查询
	 *
	 * @param dealer
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "经销商表-分页列表查询")
	@Operation(summary="经销商表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<Dealer>> queryPageList(Dealer dealer,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        // 自定义查询规则
        Map<String, QueryRuleEnum> customeRuleMap = new HashMap<>();
        // 自定义多选的查询规则为：LIKE_WITH_OR
        customeRuleMap.put("type", QueryRuleEnum.LIKE_WITH_OR);
        QueryWrapper<Dealer> queryWrapper = QueryGenerator.initQueryWrapper(dealer, req.getParameterMap(),customeRuleMap);
		Page<Dealer> page = new Page<Dealer>(pageNo, pageSize);
		IPage<Dealer> pageList = dealerService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param dealer
	 * @return
	 */
	@AutoLog(value = "经销商表-添加")
	@Operation(summary="经销商表-添加")
	@RequiresPermissions("dealer:dealer:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody Dealer dealer) {
		dealerService.save(dealer);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param dealer
	 * @return
	 */
	@AutoLog(value = "经销商表-编辑")
	@Operation(summary="经销商表-编辑")
	@RequiresPermissions("dealer:dealer:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody Dealer dealer) {
		dealerService.updateById(dealer);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "经销商表-通过id删除")
	@Operation(summary="经销商表-通过id删除")
	@RequiresPermissions("dealer:dealer:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		dealerService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "经销商表-批量删除")
	@Operation(summary="经销商表-批量删除")
	@RequiresPermissions("dealer:dealer:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.dealerService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "经销商表-通过id查询")
	@Operation(summary="经销商表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<Dealer> queryById(@RequestParam(name="id",required=true) String id) {
		Dealer dealer = dealerService.getById(id);
		if(dealer==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(dealer);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param dealer
    */
    @RequiresPermissions("dealer:dealer:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Dealer dealer) {
        return super.exportXls(request, dealer, Dealer.class, "经销商表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("dealer:dealer:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, Dealer.class);
    }

}
