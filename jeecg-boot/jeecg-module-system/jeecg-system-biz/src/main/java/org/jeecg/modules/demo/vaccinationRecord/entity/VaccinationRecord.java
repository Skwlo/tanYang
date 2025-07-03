package org.jeecg.modules.demo.vaccinationRecord.entity;

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
 * @Description: 免疫记录表
 * @Author: jeecg-boot
 * @Date:   2025-06-13
 * @Version: V1.0
 */
@Data
@TableName("vaccination_record")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="免疫记录表")
public class VaccinationRecord implements Serializable {
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
	/**畜只 ID（可选，群体免疫时为空）*/
	@Excel(name = "畜只 ID（可选，群体免疫时为空）", width = 15)
    @Schema(description = "畜只 ID（可选，群体免疫时为空）")
    private String livestockId;
    /**疫苗 ID*/
    @Excel(name = "疫苗 ID", width = 15)
    @Dict(dictTable = "inventory", dicText = "material_id", dicCode = "material_id")
    @Schema(description = "疫苗 ID")
    private String vaccineId;

    /**免疫日期*/
	@Excel(name = "免疫日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Schema(description = "免疫日期")
    private Date vaccinationDate;
	/**使用剂量*/
	@Excel(name = "使用剂量", width = 15)
    @Schema(description = "使用剂量")
    private BigDecimal dosage;
	/**注射部位*/
	@Excel(name = "注射部位", width = 15)
    @Schema(description = "注射部位")
    private String injectionSite;
	/**合格指标*/
	@Excel(name = "合格指标", width = 15)
    @Schema(description = "合格指标")
    private String standard;
	/**免疫范围（个体 / 群体）*/
	@Excel(name = "免疫范围（个体 / 群体）", width = 15)
    @Dict(dicCode = "mianyiFanwei")
    @Schema(description = "免疫范围（个体 / 群体）")
    private String scope;
	/**说明*/
	@Excel(name = "说明", width = 15)
    @Schema(description = "说明")
    private String note;
}
