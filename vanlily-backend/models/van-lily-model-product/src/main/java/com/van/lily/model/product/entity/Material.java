package com.van.lily.model.product.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.van.lily.commons.define.data.SqlFieldConstants;
import com.van.lily.model.product.define.enums.EnumMaterialType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value = "原材料模版")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Material {

    @TableLogic(value = SqlFieldConstants.LIVE, delval = SqlFieldConstants.DIE)
    private Short live;
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private Date published;

    @ApiModelProperty("原料编号")
    private String number;
    @ApiModelProperty("原料名称")
    private String name;
    @ApiModelProperty("描述信息")
    private String desc;
    @ApiModelProperty("原料类型")
    private EnumMaterialType typed;

    @ApiModelProperty("保质期（小时）")
    private Integer hour_live;
    @ApiModelProperty("新鲜期（小时）")
    private Integer hour_fresh;

    @ApiModelProperty("最低入货价钱")
    private BigDecimal price_lowest_restock;
    @ApiModelProperty("最新入货价钱")
    private BigDecimal price_newest_restock;
}
