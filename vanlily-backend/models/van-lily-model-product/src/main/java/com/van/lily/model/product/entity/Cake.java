package com.van.lily.model.product.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.van.lily.commons.core.annotations.TableJsonField;
import com.van.lily.commons.define.data.SqlFieldConstants;
import com.van.lily.model.product.define.ProductTableNameDefine;
import com.van.lily.model.product.define.enums.EnumCakeLifeType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value = "蛋糕")
@TableName(ProductTableNameDefine.CAKE)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cake implements Serializable {

    @TableLogic(value = SqlFieldConstants.LIVE, delval = SqlFieldConstants.DIE)
    private Short live;
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private Date published;
    private Integer version;

    @ApiModelProperty("蛋糕系列编号")
    private String number;
    @ApiModelProperty("蛋糕名称")
    private String name;
    @ApiModelProperty("描述信息")
    @TableJsonField()
    private String description;

    @ApiModelProperty("蛋糕保质期（小时）")
    private Integer hour_live;
    @ApiModelProperty("新鲜食用期（小时）")
    private Integer hour_fresh;

    @ApiModelProperty("蛋糕生命类型")
    private EnumCakeLifeType type_life;

    @ApiModelProperty("附属品选择表单")
    @TableJsonField()
    private String variations;
    @ApiModelProperty("蛋糕尺寸信息")
    @TableJsonField()
    private String sizes;

    @ApiModelProperty("蛋糕的图册")
    @TableJsonField()
    private String images;
    @ApiModelProperty("蛋糕的标签集合")
    @TableJsonField()
    private String tags;

    @ApiModelProperty("推荐系数")
    private Short awesome;

    @ApiModelProperty("设计该蛋糕时候的备注")
    private String remark_creat;

    @ApiModelProperty("是否允许贩卖")
    private Short is_allow;
    @ApiModelProperty("是否是限定商品")
    private Short is_restricted;
    @ApiModelProperty("下次上架时间")
    private String next_open_time;
    @ApiModelProperty("下次下架时间")
    private String next_close_time;
}
