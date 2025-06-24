package org.jeecg.modules.demo.transferRecord.controller;

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

import com.alibaba.druid.util.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.query.QueryRuleEnum;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.livestock.entity.Livestock;
import org.jeecg.modules.demo.livestock.service.ILivestockService;
import org.jeecg.modules.demo.transferRecord.entity.TransferRecord;
import org.jeecg.modules.demo.transferRecord.service.ITransferRecordService;

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
 * @Description: 转群记录表
 * @Author: jeecg-boot
 * @Date:   2025-06-13
 * @Version: V1.0
 */
@Tag(name="转群记录表")
@RestController
@RequestMapping("/transferRecord/transferRecord")
@Slf4j
public class TransferRecordController extends JeecgController<TransferRecord, ITransferRecordService> {
	@Autowired
	private ITransferRecordService transferRecordService;

	 @Autowired
	 private ILivestockService livestockService;

	/**
	 * 分页列表查询
	 *
	 * @param transferRecord
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "转群记录表-分页列表查询")
	@Operation(summary="转群记录表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<TransferRecord>> queryPageList(TransferRecord transferRecord,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<TransferRecord> queryWrapper = QueryGenerator.initQueryWrapper(transferRecord, req.getParameterMap());
		Page<TransferRecord> page = new Page<TransferRecord>(pageNo, pageSize);
		IPage<TransferRecord> pageList = transferRecordService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param transferRecord
	 * @return
	 */
	@AutoLog(value = "转群记录表-添加")
	@Operation(summary="转群记录表-添加")
	@RequiresPermissions("transferRecord:transfer_record:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody TransferRecord transferRecord) {
		try {
			// 校验必填字段
			if (transferRecord.getTransferRecord() == null) {
				return Result.error("畜只ID不能为空");
			}

			if (transferRecord.getFromShedPenId() == null) {
				return Result.error("原棚栏ID不能为空");
			}

			if (transferRecord.getToShedPenId() == null) {
				return Result.error("目标棚栏ID不能为空");
			}

			if (transferRecord.getTransferDate() == null) {
				return Result.error("转群日期不能为空");
			}

			// 查询关联的畜只信息
			Livestock livestock = livestockService.getByLivestockId(transferRecord.getTransferRecord());
			if (livestock == null) {
				return Result.error("未找到对应畜只信息，ID：" + transferRecord.getTransferRecord());
			}

			// 校验畜只状态（非死亡/已售状态才能转群）
			if ("死亡".equals(livestock.getStatus()) || "已售".equals(livestock.getStatus()) ||"淘汰".equals(livestock.getStatus()) ) {
				return Result.error("畜只状态为" + livestock.getStatus() + "，不可转群");
			}

			// 保存转群记录
			boolean saveResult = transferRecordService.save(transferRecord);
			if (!saveResult) {
				return Result.error("转群记录保存失败");
			}

			// 更新畜只信息
			livestock.setCurrentShedPenId(transferRecord.getToShedPenId());
			livestock.setUpdateTime(transferRecord.getTransferDate());

			// 根据目标棚栏ID更新畜只阶段（示例逻辑，可根据实际业务调整）
			if (transferRecord.getToShedPenId().contains("幼崽")) {
				livestock.setCurrentStage("幼崽");
			} else if (transferRecord.getToShedPenId().contains("育肥")) {
				livestock.setCurrentStage("育肥");
			} else if (transferRecord.getToShedPenId().contains("成年")) {
				livestock.setCurrentStage("成年");
			}

			// 更新畜只状态为"正常"（转群后默认状态）
			livestock.setStatus("正常");

			boolean updateResult = livestockService.updateById(livestock);
			if (!updateResult) {
				throw new RuntimeException("更新畜只信息失败");
			}

			return Result.OK("转群记录添加成功，畜只已转移至：" + transferRecord.getToShedPenId());
		} catch (Exception e) {
			log.error("添加转群记录失败：", e);
			return Result.error("添加失败：" + e.getMessage());
		}
	}

	/**
	 *  编辑
	 *
	 * @param transferRecord
	 * @return
	 */
	@AutoLog(value = "转群记录表-编辑")
	@Operation(summary="转群记录表-编辑")
	@RequiresPermissions("transferRecord:transfer_record:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody TransferRecord transferRecord) {
		try {
			// 查询原始转群记录
			TransferRecord oldRecord = transferRecordService.getById(transferRecord.getId());
			if (oldRecord == null) {
				return Result.error("未找到对应转群记录");
			}

			// 查询关联的畜只信息
			Livestock livestock1 = livestockService.getByLivestockId(transferRecord.getTransferRecord());
			if (livestock1 == null) {
				return Result.error("未找到对应畜只信息，ID：" + transferRecord.getTransferRecord());
			}

			// 更新转群记录
			boolean updateResult = transferRecordService.updateById(transferRecord);
			if (!updateResult) {
				return Result.error("转群记录更新失败");
			}

			// 如果转群后棚栏有变化，同步更新畜只信息
			if (!oldRecord.getToShedPenId().equals(transferRecord.getToShedPenId())) {
				// 通过livestockId查询关联的畜只信息
				Livestock livestock = livestockService.getByLivestockId(oldRecord.getTransferRecord());
				if (livestock != null) {
					livestock.setCurrentShedPenId(transferRecord.getToShedPenId());
					livestock.setUpdateTime(transferRecord.getTransferDate());

					// 更新畜只阶段
					if (transferRecord.getToShedPenId().contains("幼崽")) {
						livestock.setCurrentStage("幼崽");
					} else if (transferRecord.getToShedPenId().contains("育肥")) {
						livestock.setCurrentStage("育肥");
					}

					livestockService.updateById(livestock);
				}
			}

			return Result.OK("编辑成功!");
		} catch (Exception e) {
			log.error("编辑转群记录失败：", e);
			return Result.error("编辑转群记录失败：" + e.getMessage());
		}
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "转群记录表-通过id删除")
	@Operation(summary="转群记录表-通过id删除")
	@RequiresPermissions("transferRecord:transfer_record:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		transferRecordService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "转群记录表-批量删除")
	@Operation(summary="转群记录表-批量删除")
	@RequiresPermissions("transferRecord:transfer_record:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.transferRecordService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "转群记录表-通过id查询")
	@Operation(summary="转群记录表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<TransferRecord> queryById(@RequestParam(name="id",required=true) String id) {
		TransferRecord transferRecord = transferRecordService.getById(id);
		if(transferRecord==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(transferRecord);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param transferRecord
    */
    @RequiresPermissions("transferRecord:transfer_record:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, TransferRecord transferRecord) {
        return super.exportXls(request, transferRecord, TransferRecord.class, "转群记录表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("transferRecord:transfer_record:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, TransferRecord.class);
    }

}
