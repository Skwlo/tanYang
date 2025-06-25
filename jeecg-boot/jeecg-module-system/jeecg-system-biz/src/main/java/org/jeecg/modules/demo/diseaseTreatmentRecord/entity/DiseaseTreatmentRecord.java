package org.jeecg.modules.demo.diseaseTreatmentRecord.entity;

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
 * @Description: 疾病治疗记录表
 * @Author: jeecg-boot
 * @Date:   2025-06-13
 * @Version: V1.0
 */
@Data
@TableName("disease_treatment_record")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="疾病治疗记录表")
public class DiseaseTreatmentRecord implements Serializable {
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
	/**发病日期*/
	@Excel(name = "发病日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Schema(description = "发病日期")
    private Date onsetDate;
	/**疾病类型*/
	@Excel(name = "疾病类型", width = 15)
    @Dict(dicCode = "jibingWay")
    @Schema(description = "疾病类型")
    private String diseaseType;
	/**主要症状*/
	@Excel(name = "主要症状", width = 15)
    @Schema(description = "主要症状")
    private String symptoms;
	/**发病原因*/
	@Excel(name = "发病原因", width = 15)
    @Schema(description = "发病原因")
    private String cause;
	/**治疗方案 ID*/
	@Excel(name = "治疗方案 ID", width = 15)
    @Schema(description = "治疗方案 ID")
    private String treatmentPlanId;
	/**兽医师*/
	@Excel(name = "兽医师", width = 15)
    @Schema(description = "兽医师")
    private String veterinarian;
	/**是否治愈（1/0）	*/
	@Excel(name = "是否治愈（1/0）	", width = 15)
    @Dict(dicCode = "shifouzhiyu")
    @Schema(description = "是否治愈（1/0）	")
    private String isCured;
	/**治疗说明*/
	@Excel(name = "治疗说明", width = 15)
    @Schema(description = "治疗说明")
    private String note;
}
