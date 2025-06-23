package org.jeecg.modules.demo.quarantineRecord.entity;

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
 * @Description: 检疫记录表
 * @Author: jeecg-boot
 * @Date:   2025-06-13
 * @Version: V1.0
 */
@Data
@TableName("quarantine_record")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="检疫记录表")
public class QuarantineRecord implements Serializable {
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
	/**畜只 ID（可选，群体检疫时为空）*/
	@Excel(name = "畜只 ID（可选，群体检疫时为空）", width = 15)
    @Schema(description = "畜只 ID（可选，群体检疫时为空）")
    private String livestockId;
	/**棚栏 ID（群体检疫时使用）*/
	@Excel(name = "棚栏 ID（群体检疫时使用）", width = 15)
    @Schema(description = "棚栏 ID（群体检疫时使用）")
    private String shedPenId;
	/**检疫日期*/
	@Excel(name = "检疫日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Schema(description = "检疫日期")
    private Date quarantineDate;
	/**检疫项目*/
	@Excel(name = "检疫项目", width = 15)
    @Schema(description = "检疫项目")
    private String item;
	/**检疫结果*/
	@Excel(name = "检疫结果", width = 15)
    @Schema(description = "检疫结果")
    private String result;
	/**检疫报告文件路径*/
	@Excel(name = "检疫报告文件路径", width = 15)
    @Schema(description = "检疫报告文件路径")
    private String reportPath;
}
