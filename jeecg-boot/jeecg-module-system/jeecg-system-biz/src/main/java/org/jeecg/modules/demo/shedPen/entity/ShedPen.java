package org.jeecg.modules.demo.shedPen.entity;

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
 * @Description: 场棚栏表
 * @Author: jeecg-boot
 * @Date:   2025-06-17
 * @Version: V1.0
 */
@Data
@TableName("shed_pen")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="场棚栏表")
public class ShedPen implements Serializable {
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
	/**名称*/
	@Excel(name = "名称", width = 15)
    @Schema(description = "名称")
    private String name;
	/**类型（场 / 棚 / 栏）*/
	@Excel(name = "类型（场 / 棚 / 栏）", width = 15)
    @Dict(dicCode = "Changpengway")
    @Schema(description = "类型（场 / 棚 / 栏）")
    private String type;
	/**父级 ID（场无父级，棚父级为场，栏父级为棚）*/
	@Excel(name = "父级 ID（场无父级，棚父级为场，栏父级为棚）", width = 15)
    @Schema(description = "父级 ID（场无父级，棚父级为场，栏父级为棚）")
    private String parentId;
	/**容量（可容纳畜只数量）*/
	@Excel(name = "容量（可容纳畜只数量）", width = 15)
    @Schema(description = "容量（可容纳畜只数量）")
    private Integer capacity;
	/**说明*/
	@Excel(name = "说明", width = 15)
    @Schema(description = "说明")
    private String note;
	/**棚栏 ID*/
	@Excel(name = "棚栏 ID", width = 15)
    @Schema(description = "棚栏 ID")
    private String shedPenId;
}
