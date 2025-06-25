package org.jeecg.modules.demo.bodyMeasurement.entity;

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
 * @Description: 体尺测量记录表
 * @Author: jeecg-boot
 * @Date:   2025-06-13
 * @Version: V1.0
 */
@Data
@TableName("body_measurement")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="体尺测量记录表")
public class BodyMeasurement implements Serializable {
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
	/**畜只 ID*/
	@Excel(name = "畜只 ID", width = 15)
    @Schema(description = "畜只 ID")
    private String livestockId;
	/**测量日期*/
	@Excel(name = "测量日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Schema(description = "测量日期")
    private Date measureDate;
	/**测量阶段（羔羊期 / 育肥期等）*/
	@Excel(name = "测量阶段（羔羊期 / 育肥期等）", width = 15)
    @Dict(dicCode = "celiangjieduan")
    @Schema(description = "测量阶段（羔羊期 / 育肥期等）")
    private String measureStage;
	/**月龄*/
	@Excel(name = "月龄", width = 15)
    @Schema(description = "月龄")
    private Integer age;
	/**体高 (cm)*/
	@Excel(name = "体高 (cm)", width = 15)
    @Schema(description = "体高 (cm)")
    private BigDecimal bodyHeight;
	/**体重(kg)*/
	@Excel(name = "体重(kg)", width = 15)
    @Schema(description = "体重(kg)")
    private BigDecimal bodyWeight;
	/**体长(cm)*/
	@Excel(name = "体长(cm)", width = 15)
    @Schema(description = "体长(cm)")
    private BigDecimal bodyLength;
	/**胸围(cm)*/
	@Excel(name = "胸围(cm)", width = 15)
    @Schema(description = "胸围(cm)")
    private BigDecimal bust;
	/**管围(cm)*/
	@Excel(name = "管围(cm)", width = 15)
    @Schema(description = "管围(cm)")
    private BigDecimal pipeCircumference;
}
