package org.jeecg.modules.demo.stockOut.entity;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.jeecg.common.aspect.annotation.Dict;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 出库记录表
 * @Author: jeecg-boot
 * @Date:   2025-06-13
 * @Version: V1.0
 */
@Data
@TableName("stock_out")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="出库记录表")
public class StockOut implements Serializable {
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
	/**物资 ID*/
	@Excel(name = "物资 ID", width = 15)
    @Schema(description = "物资 ID")
    private String materialId;
	/**出库日期*/
	@Excel(name = "出库日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Schema(description = "出库日期")
    private Date outDate;
	/**出库数量*/
	@Excel(name = "出库数量", width = 15)
    @Schema(description = "出库数量")
    private BigDecimal quantity;
	/**用途（喂食 / 治疗）*/
	@Excel(name = "用途（喂食 / 治疗）", width = 15)
    @Dict(dicCode = "weishizhiliao")
    @Schema(description = "用途（喂食 / 治疗）")
    private String purpose;
	/**操作人*/
	@Excel(name = "操作人", width = 15)
    @Schema(description = "操作人")
    private String operator;
	/**说明*/
	@Excel(name = "说明", width = 15)
    @Schema(description = "说明")
    private String note;
}
