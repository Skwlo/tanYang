package org.jeecg.modules.demo.reproductionRecord.entity;

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
 * @Description: 繁殖记录表
 * @Author: jeecg-boot
 * @Date:   2025-06-13
 * @Version: V1.0
 */
@Data
@TableName("reproduction_record")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="繁殖记录表")
public class ReproductionRecord implements Serializable {
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
	/**配种记录 ID*/
	@Excel(name = "配种记录 ID", width = 15)
    @Schema(description = "配种记录 ID")
    private String breedingId;
	/**产崽日期*/
	@Excel(name = "产崽日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Schema(description = "产崽日期")
    private Date reproductionDate;
	/**产崽数量*/
	@Excel(name = "产崽数量", width = 15)
    @Dict(dicCode = "chanzanNumber")
    @Schema(description = "产崽数量")
    private Integer offspringCount;
	/**活崽数量*/
	@Excel(name = "活崽数量", width = 15)
    @Dict(dicCode = "huozaiNumber")
    @Schema(description = "活崽数量")
    private Integer liveOffspringCount;
	/**产崽状态（正常 / 难产）*/
	@Excel(name = "产崽状态（正常 / 难产）", width = 15)
    @Dict(dicCode = "huozaiStatue")
    @Schema(description = "产崽状态（正常 / 难产）")
    private String status;
}
