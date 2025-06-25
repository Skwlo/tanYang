package org.jeecg.modules.demo.feedingRecord.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import org.jeecg.common.constant.ProvinceCityArea;
import org.jeecg.common.util.SpringContextUtils;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 喂食记录表
 * @Author: jeecg-boot
 * @Date:   2025-06-13
 * @Version: V1.0
 */
@Data
@TableName("feeding_record")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="喂食记录表")
public class FeedingRecord implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "主键")
    private String id;
	/**创建人*/
    @Schema(description = "创建人")
    private String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Schema(description = "创建日期")
    private Date createTime;
	/**更新人*/
    @Schema(description = "更新人")
    private String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Schema(description = "更新日期")
    private Date updateTime;
	/**所属部门*/
    @Schema(description = "所属部门")
    private String sysOrgCode;
	/**喂食日期*/
	@Excel(name = "喂食日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Schema(description = "喂食日期")
    private Date feedDate;
	/**喂食棚栏 ID*/
	@Excel(name = "喂食棚栏 ID", width = 15)
    @Schema(description = "喂食棚栏 ID")
    private String shedPenId;
	/**使用配方 ID*/
	@Excel(name = "使用配方 ID", width = 15)
    @Schema(description = "使用配方 ID")
    private String formulaId;
	/**投料类别（精料 / 粗料）*/
	@Excel(name = "投料类别（精料 / 粗料）", width = 15)
    @Dict(dicCode = "touliao")
    @Schema(description = "投料类别（精料 / 粗料）")
    private String feedType;
	/**喂食数量 (kg)*/
	@Excel(name = "喂食数量 (kg)", width = 15)
    @Schema(description = "喂食数量 (kg)")
    private BigDecimal quantity;
	/**操作人*/
	@Excel(name = "操作人", width = 15)
    @Dict(dicCode = "caozuoren")
    @Schema(description = "操作人")
    private String operator;
}
