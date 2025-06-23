package org.jeecg.modules.demo.stockIn.entity;

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
 * @Description: 入库记录表
 * @Author: jeecg-boot
 * @Date:   2025-06-13
 * @Version: V1.0
 */
@Data
@TableName("stock_in")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="入库记录表")
public class StockIn implements Serializable {
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
	/**入库日期*/
	@Excel(name = "入库日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Schema(description = "入库日期")
    private Date inDate;
	/**入库数量*/
	@Excel(name = "入库数量", width = 15)
    @Schema(description = "入库数量")
    private BigDecimal quantity;
	/**单价*/
	@Excel(name = "单价", width = 15)
    @Schema(description = "单价")
    private BigDecimal unitPrice;
	/**总价*/
	@Excel(name = "总价", width = 15)
    @Schema(description = "总价")
    private BigDecimal totalPrice;
	/**经销商 ID*/
	@Excel(name = "经销商 ID", width = 15)
    @Schema(description = "经销商 ID")
    private String dealerId;
	/**运费*/
	@Excel(name = "运费", width = 15)
    @Schema(description = "运费")
    private BigDecimal freight;
	/**装卸费*/
	@Excel(name = "装卸费", width = 15)
    @Schema(description = "装卸费")
    private BigDecimal handlingFee;
	/**其他费用*/
	@Excel(name = "其他费用", width = 15)
    @Schema(description = "其他费用")
    private BigDecimal otherFee;
	/**说明*/
	@Excel(name = "说明", width = 15)
    @Schema(description = "说明")
    private String note;
}
