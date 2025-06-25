package org.jeecg.modules.demo.breedingRecord.entity;

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
 * @Description: 配种记录表
 * @Author: jeecg-boot
 * @Date:   2025-06-13
 * @Version: V1.0
 */
@Data
@TableName("breeding_record")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="配种记录表")
public class BreedingRecord implements Serializable {
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
	/**公畜 ID*/
	@Excel(name = "公畜 ID", width = 15)
    @Schema(description = "公畜 ID")
    private String maleLivestockId;
	/**母畜 ID*/
	@Excel(name = "母畜 ID", width = 15)
    @Schema(description = "母畜 ID")
    private String femaleLivestockId;
	/**配种日期*/
	@Excel(name = "配种日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Schema(description = "配种日期")
    private Date breedingDate;
	/**预产日期*/
	@Excel(name = "预产日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Schema(description = "预产日期")
    private Date expectedDeliveryDate;
	/**配种方式（自然 / 人工）*/
	@Excel(name = "配种方式（自然 / 人工）", width = 15)
    @Dict(dicCode = "peizhong-way")
    @Schema(description = "配种方式（自然 / 人工）")
    private String method;
	/**配种状态（成功 / 失败）*/
	@Excel(name = "配种状态（成功 / 失败）", width = 15)
    @Dict(dicCode = "Peizhong-Zhuangtai")
    @Schema(description = "配种状态（成功 / 失败）")
    private String status;
	/**是否繁育（1/0）*/
	@Excel(name = "是否繁育（1/0）", width = 15)
    @Dict(dicCode = "isBreeding")
    @Schema(description = "是否繁育（1/0）")
    private String isBreeding;
}
