package org.jeecg.modules.demo.material.entity;

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
 * @Description: 物资表
 * @Author: jeecg-boot
 * @Date:   2025-06-17
 * @Version: V1.0
 */
@Data
@TableName("material")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="物资表")
public class Material implements Serializable {
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
	/**物资名称*/
	@Excel(name = "物资名称", width = 15)
    @Schema(description = "物资名称")
    private String name;
	/**物资类型（饲料 / 药品 / 疫苗）*/
	@Excel(name = "物资类型（饲料 / 药品 / 疫苗）", width = 15)
    @Dict(dicCode = "wuzileixing")
    @Schema(description = "物资类型（饲料 / 药品 / 疫苗）")
    private String type;
	/**规格含量*/
	@Excel(name = "规格含量", width = 15)
    @Schema(description = "规格含量")
    private String specification;
	/**计量单位*/
	@Excel(name = "计量单位", width = 15)
    @Schema(description = "计量单位")
    private String unit;
	/**	警戒数量*/
	@Excel(name = "	警戒数量", width = 15)
    @Schema(description = "	警戒数量")
    private Integer alertQuantity;
	/**是否是药品疫苗（1/0）*/
	@Excel(name = "是否是药品疫苗（1/0）", width = 15)
    @Dict(dicCode = "yaopinyimiao")
    @Schema(description = "是否是药品疫苗（1/0）")
    private String isMedicine;
	/**有效期天数（用于计算过期时间）*/
	@Excel(name = "有效期天数（用于计算过期时间）", width = 15)
    @Schema(description = "有效期天数（用于计算过期时间）")
    private Integer expirationDays;
	/**说明*/
	@Excel(name = "说明", width = 15)
    @Schema(description = "说明")
    private String note;
	/**物资 ID*/
	@Excel(name = "物资 ID", width = 15)
    @Schema(description = "物资 ID")
    private String materialId;
}
