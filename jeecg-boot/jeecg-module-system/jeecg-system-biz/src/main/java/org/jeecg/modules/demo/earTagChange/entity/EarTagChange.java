package org.jeecg.modules.demo.earTagChange.entity;

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
 * @Description: 耳号更换记录表
 * @Author: jeecg-boot
 * @Date:   2025-06-13
 * @Version: V1.0
 */
@Data
@TableName("ear_tag_change")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="耳号更换记录表")
public class EarTagChange implements Serializable {
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
	/**旧普通耳号*/
	@Excel(name = "旧普通耳号", width = 15)
    @Schema(description = "旧普通耳号")
    private String oldCommonEarTag;
	/**新普通耳号*/
	@Excel(name = "新普通耳号", width = 15)
    @Schema(description = "新普通耳号")
    private String newCommonEarTag;
	/**旧电子耳号*/
	@Excel(name = "旧电子耳号", width = 15)
    @Schema(description = "旧电子耳号")
    private String oldElectronicEarTag;
	/**新电子耳号*/
	@Excel(name = "新电子耳号", width = 15)
    @Schema(description = "新电子耳号")
    private String newElectronicEarTag;
	/**更换日期*/
	@Excel(name = "更换日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Schema(description = "更换日期")
    private Date changeDate;
	/**更换原因*/
	@Excel(name = "更换原因", width = 15)
    @Schema(description = "更换原因")
    private String reason;
}
