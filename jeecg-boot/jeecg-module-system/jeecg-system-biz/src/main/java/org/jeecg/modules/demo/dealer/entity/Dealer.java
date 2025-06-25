package org.jeecg.modules.demo.dealer.entity;

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
 * @Description: 经销商表
 * @Author: jeecg-boot
 * @Date:   2025-06-17
 * @Version: V1.0
 */
@Data
@TableName("dealer")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="经销商表")
public class Dealer implements Serializable {
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
	/**经销商名称*/
	@Excel(name = "经销商名称", width = 15)
    @Schema(description = "经销商名称")
    private String name;
	/**销售类型（饲料 / 药品 / 畜只）*/
	@Excel(name = "销售类型（饲料 / 药品 / 畜只）", width = 15)
    @Dict(dicCode = "xiangshouWay")
    @Schema(description = "销售类型（饲料 / 药品 / 畜只）")
    private String type;
	/**地址*/
	@Excel(name = "地址", width = 15)
    @Schema(description = "地址")
    private String address;
	/**联系人*/
	@Excel(name = "联系人", width = 15)
    @Schema(description = "联系人")
    private String contactPerson;
	/**电话*/
	@Excel(name = "电话", width = 15)
    @Schema(description = "电话")
    private String phone;
	/**说明*/
	@Excel(name = "说明", width = 15)
    @Schema(description = "说明")
    private String note;
	/**经销商 ID*/
	@Excel(name = "经销商 ID", width = 15)
    @Schema(description = "经销商 ID")
    private String dealerId;
}
