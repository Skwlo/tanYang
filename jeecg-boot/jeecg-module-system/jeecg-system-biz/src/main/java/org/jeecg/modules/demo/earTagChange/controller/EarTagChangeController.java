package org.jeecg.modules.demo.earTagChange.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.demo.earTagChange.entity.EarTagChange;
import org.jeecg.modules.demo.earTagChange.service.IEarTagChangeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.demo.livestock.entity.Livestock;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.demo.livestock.service.ILivestockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

 /**
 * @Description: 耳号更换记录表
 * @Author: jeecg-boot
 * @Date:   2025-06-13
 * @Version: V1.0
 */
@Tag(name="耳号更换记录表")
@RestController
@RequestMapping("/earTagChange/earTagChange")
@Slf4j
public class EarTagChangeController extends JeecgController<EarTagChange, IEarTagChangeService> {
	@Autowired
	private IEarTagChangeService earTagChangeService;

	@Autowired
	private ILivestockService livestockService;

	/**
	 * 分页列表查询
	 *
	 * @param earTagChange
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "耳号更换记录表-分页列表查询")
	@Operation(summary="耳号更换记录表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<EarTagChange>> queryPageList(EarTagChange earTagChange,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<EarTagChange> queryWrapper = QueryGenerator.initQueryWrapper(earTagChange, req.getParameterMap());
		Page<EarTagChange> page = new Page<EarTagChange>(pageNo, pageSize);
		IPage<EarTagChange> pageList = earTagChangeService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param earTagChange
	 * @return
	 */
	@AutoLog(value = "耳号更换记录表-添加")
	@Operation(summary="耳号更换记录表-添加")
	@RequiresPermissions("earTagChange:ear_tag_change:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody EarTagChange earTagChange) {
		try {
			// 校验必填字段
			if (earTagChange.getLivestockId() == null) {
				return Result.error("畜只ID不能为空");
			}

			if (earTagChange.getNewCommonEarTag() == null) {
				return Result.error("新普通耳号不能为空");
			}

			if (earTagChange.getChangeDate() == null) {
				return Result.error("更换日期不能为空");
			}

			// 查询关联的畜只信息（使用livestockId查询）
			Livestock livestock = livestockService.getByLivestockId(earTagChange.getLivestockId());
			if (livestock == null) {
				return Result.error("未找到对应畜只信息，ID：" + earTagChange.getLivestockId());
			}

			// 校验畜只状态（非死亡/已售状态才能更换耳号）
			if ("死亡".equals(livestock.getStatus()) || "已售".equals(livestock.getStatus())) {
				return Result.error("畜只状态为" + livestock.getStatus() + "，不可更换耳号");
			}

			// 检查新耳号是否已被使用
			QueryWrapper<Livestock> queryWrapper = new QueryWrapper<>();
			queryWrapper.eq("common_ear_tag", earTagChange.getNewCommonEarTag());
			Livestock existingLivestock = livestockService.getOne(queryWrapper);

			if (existingLivestock != null && !existingLivestock.getId().equals(livestock.getId())) {
				return Result.error("新耳号已被其他畜只使用：" + earTagChange.getNewCommonEarTag());
			}

			// 保存耳号更换记录
			earTagChange.setOldCommonEarTag(livestock.getCommonEarTag()); // 记录旧耳号
			boolean saveResult = earTagChangeService.save(earTagChange);
			if (!saveResult) {
				return Result.error("耳号更换记录保存失败");
			}

			// 更新畜只耳号信息
			livestock.setCommonEarTag(earTagChange.getNewCommonEarTag());
			livestock.setUpdateTime(earTagChange.getChangeDate());

			boolean updateResult = livestockService.updateById(livestock);
			if (!updateResult) {
				throw new RuntimeException("更新畜只耳号信息失败");
			}

			return Result.OK("耳号更换记录添加成功，畜只新耳号：" + earTagChange.getNewCommonEarTag());
		} catch (Exception e) {
			log.error("添加耳号更换记录失败：", e);
			return Result.error("添加失败：" + e.getMessage());
		}
	}



	 /**
	 *  编辑
	 *
	 * @param earTagChange
	 * @return
	 */
	@AutoLog(value = "耳号更换记录表-编辑")
	@Operation(summary="耳号更换记录表-编辑")
	@RequiresPermissions("earTagChange:ear_tag_change:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody EarTagChange earTagChange) {
		try {
			// 校验ID
			if (earTagChange.getId() == null) {
				return Result.error("耳号更换记录ID不能为空");
			}

			// 查询原始耳号更换记录
			EarTagChange oldRecord = earTagChangeService.getById(earTagChange.getId());
			if (oldRecord == null) {
				return Result.error("未找到对应耳号更换记录");
			}

			// 查询关联的畜只信息
			Livestock livestock = livestockService.getByLivestockId(oldRecord.getLivestockId());
			if (livestock == null) {
				return Result.error("未找到对应畜只信息，ID：" + oldRecord.getLivestockId());
			}

			// 如果新耳号有变化，需要进行校验
			if (!oldRecord.getNewCommonEarTag().equals(earTagChange.getNewCommonEarTag())) {
				// 检查新耳号是否已被使用
				QueryWrapper<Livestock> queryWrapper = new QueryWrapper<>();
				queryWrapper.eq("common_ear_tag", earTagChange.getNewCommonEarTag());
				Livestock existingLivestock = livestockService.getOne(queryWrapper);

				if (existingLivestock != null && !existingLivestock.getId().equals(livestock.getId())) {
					return Result.error("新耳号已被其他畜只使用：" + earTagChange.getNewCommonEarTag());
				}

				// 更新畜只耳号信息
				livestock.setCommonEarTag(earTagChange.getNewCommonEarTag());
				livestock.setUpdateTime(earTagChange.getChangeDate());

				boolean updateResult = livestockService.updateById(livestock);
				if (!updateResult) {
					throw new RuntimeException("更新畜只耳号信息失败");
				}
			}

			// 更新耳号更换记录
			boolean updateResult = earTagChangeService.updateById(earTagChange);
			if (!updateResult) {
				return Result.error("更新耳号更换记录失败");
			}

			return Result.OK("耳号更换记录编辑成功");
		} catch (Exception e) {
			log.error("编辑耳号更换记录失败：", e);
			return Result.error("编辑失败：" + e.getMessage());
		}
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "耳号更换记录表-通过id删除")
	@Operation(summary="耳号更换记录表-通过id删除")
	@RequiresPermissions("earTagChange:ear_tag_change:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		earTagChangeService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "耳号更换记录表-批量删除")
	@Operation(summary="耳号更换记录表-批量删除")
	@RequiresPermissions("earTagChange:ear_tag_change:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.earTagChangeService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "耳号更换记录表-通过id查询")
	@Operation(summary="耳号更换记录表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<EarTagChange> queryById(@RequestParam(name="id",required=true) String id) {
		EarTagChange earTagChange = earTagChangeService.getById(id);
		if(earTagChange==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(earTagChange);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param earTagChange
    */
    @RequiresPermissions("earTagChange:ear_tag_change:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, EarTagChange earTagChange) {
        return super.exportXls(request, earTagChange, EarTagChange.class, "耳号更换记录表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("earTagChange:ear_tag_change:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, EarTagChange.class);
    }

}
