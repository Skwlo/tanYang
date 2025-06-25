package org.jeecg.modules.demo.treatmentPlan.entity;

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
 * @Description: 疾病治疗方案表
 * @Author: jeecg-boot
 * @Date:   2025-06-13
 * @Version: V1.0
 */
@Data
@TableName("treatment_plan")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="疾病治疗方案表")
public class TreatmentPlan implements Serializable {
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
	/**疾病类型*/
	@Excel(name = "疾病类型", width = 15)
    @Dict(dicCode = "jibingWay")
    @Schema(description = "疾病类型")
    private String diseaseType;
	/**主要症状*/
	@Excel(name = "主要症状", width = 15)
    @Schema(description = "主要症状")
    private String mainSymptoms;
	/**发病原因*/
	@Excel(name = "发病原因", width = 15)
    @Schema(description = "发病原因")
    private String cause;
	/**药物治疗种类*/
	@Excel(name = "药物治疗种类", width = 15)
    @Dict(dicCode = "medicationZhiliaoCata")
    @Schema(description = "药物治疗种类")
    private String medicationType;
	/**使用方式*/
	@Excel(name = "使用方式", width = 15)
    @Dict(dicCode = "shiyongWay")
    @Schema(description = "使用方式")
    private String usageMethod;
	/**治疗说明	*/
	@Excel(name = "治疗说明	", width = 15)
    @Schema(description = "治疗说明	")
    private String note;
}
