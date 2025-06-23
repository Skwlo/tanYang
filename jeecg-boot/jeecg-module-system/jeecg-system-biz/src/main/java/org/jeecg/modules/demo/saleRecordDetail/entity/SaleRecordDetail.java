package org.jeecg.modules.demo.saleRecordDetail.entity;

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
 * @Description: 销售明细表
 * @Author: jeecg-boot
 * @Date:   2025-06-13
 * @Version: V1.0
 */
@Data
@TableName("sale_record_detail")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="销售明细表")
public class SaleRecordDetail implements Serializable {
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
	/**销售记录 ID*/
	@Excel(name = "销售记录 ID", width = 15)
    @Schema(description = "销售记录 ID")
    private String saleRecordId;
	/**畜只 ID*/
	@Excel(name = "畜只 ID", width = 15)
    @Schema(description = "畜只 ID")
    private String livestockId;
	/**单价*/
	@Excel(name = "单价", width = 15)
    @Schema(description = "单价")
    private BigDecimal unitPrice;
	/**数量（通常为 1）*/
	@Excel(name = "数量（通常为 1）", width = 15)
    @Schema(description = "数量（通常为 1）")
    private Integer quantity;
	/**单位（头 / 公斤）*/
	@Excel(name = "单位（头 / 公斤）", width = 15)
    @Schema(description = "单位（头 / 公斤）")
    private String unit;
	/**小计金额*/
	@Excel(name = "小计金额", width = 15)
    @Schema(description = "小计金额")
    private BigDecimal amount;
}
