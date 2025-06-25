package org.jeecg.modules.demo.livestock.entity;

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
 * @Description: 畜只表
 * @Author: jeecg-boot
 * @Date:   2025-06-17
 * @Version: V1.0
 */
@Data
@TableName("livestock")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="畜只表")
public class Livestock implements Serializable {
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
	/**普通耳号*/
	@Excel(name = "普通耳号", width = 15)
    @Schema(description = "普通耳号")
    private String commonEarTag;
	/**电子耳号*/
	@Excel(name = "电子耳号", width = 15)
    @Schema(description = "电子耳号")
    private String electronicEarTag;
	/**品种（如滩羊）*/
	@Excel(name = "品种（如滩羊）", width = 15)
    @Dict(dicCode = "pinzhong")
    @Schema(description = "品种（如滩羊）")
    private String breed;
	/**类别（种羊、肉羊等）*/
	@Excel(name = "类别（种羊、肉羊等）", width = 15)
    @Dict(dicCode = "leibie")
    @Schema(description = "类别（种羊、肉羊等）")
    private String category;
	/**性别（M/F）*/
	@Excel(name = "性别（M/F）", width = 15)
    @Dict(dicCode = "xingbie")
    @Schema(description = "性别（M/F）")
    private String gender;
	/**出生日期*/
	@Excel(name = "出生日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Schema(description = "出生日期")
    private Date birthDate;
	/**来源（采购 / 自繁）*/
	@Excel(name = "来源（采购 / 自繁）", width = 15)
    @Dict(dicCode = "laiyuan")
    @Schema(description = "来源（采购 / 自繁）")
    private String source;
	/**状态（正常 / 死亡 / 淘汰 / 已售）*/
	@Excel(name = "状态（正常 / 死亡 / 淘汰 / 已售）", width = 15)
    @Dict(dicCode = "zhuangtai")
    @Schema(description = "状态（正常 / 死亡 / 淘汰 / 已售）")
    private String status;
	/**当前阶段（幼崽 / 育肥 / 成年）*/
	@Excel(name = "当前阶段（幼崽 / 育肥 / 成年）", width = 15)
    @Dict(dicCode = "dangqianjieduan")
    @Schema(description = "当前阶段（幼崽 / 育肥 / 成年）")
    private String currentStage;
	/**当前所在栅栏ID*/
	@Excel(name = "当前所在栅栏ID", width = 15)
    @Schema(description = "当前所在栅栏ID")
    private String currentShedPenId;
	/**父亲畜只 ID（可选）*/
	@Excel(name = "父亲畜只 ID（可选）", width = 15)
    @Schema(description = "父亲畜只 ID（可选）")
    private String fatherId;
	/**母亲畜只 ID（可选）*/
	@Excel(name = "母亲畜只 ID（可选）", width = 15)
    @Schema(description = "母亲畜只 ID（可选）")
    private String motherId;
	/**当前体重 (kg)（可选，可通过体尺测量更新）*/
	@Excel(name = "当前体重 (kg)（可选，可通过体尺测量更新）", width = 15)
    @Schema(description = "当前体重 (kg)（可选，可通过体尺测量更新）")
    private BigDecimal weight;
	/**采购记录 ID（可选）*/
	@Excel(name = "采购记录 ID（可选）", width = 15)
    @Schema(description = "采购记录 ID（可选）")
    private String purchaseInfoId;
	/**畜只唯一标识（电子耳号）*/
	@Excel(name = "畜只唯一标识（电子耳号）", width = 15)
    @Schema(description = "畜只唯一标识（电子耳号）")
    private String livestockId;
}
