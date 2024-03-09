package com.van.lily.model.product.front.view.cake;

import com.van.lily.commons.core.annotations.TableJsonField;
import com.van.lily.model.product.define.enums.EnumCakeLifeType;
import com.van.lily.model.product.front.view.cake.jsonCake.ViewCakeImage;
import com.van.lily.model.product.front.view.cake.jsonCake.ViewCakeTag;
import com.van.lily.model.product.front.view.cake.jsonCake.ViewCakeVariation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@ApiModel(value = "视图：蛋糕")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewCake {

    private Long id;
    private Date published;
    private Integer version;

    @ApiModelProperty("蛋糕编号")
    private String number;
    @ApiModelProperty("蛋糕名称")
    private String name;
    @ApiModelProperty("描述信息")
    private String description;

    @ApiModelProperty("蛋糕保质期（小时）")
    private Integer hour_live;
    @ApiModelProperty("新鲜食用期（小时）")
    private Integer hour_fresh;
    @ApiModelProperty("蛋糕生命类型")
    private EnumCakeLifeType type_life;

    @ApiModelProperty("蛋糕的附属品")
    private String accessories;
    @ApiModelProperty("附属品选择表单")
    private List<ViewCakeVariation> variations;
    @ApiModelProperty("蛋糕的图册")
    private List<ViewCakeImage> images;
    @ApiModelProperty("蛋糕的标签集合")
    private List<ViewCakeTag> tags;

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
