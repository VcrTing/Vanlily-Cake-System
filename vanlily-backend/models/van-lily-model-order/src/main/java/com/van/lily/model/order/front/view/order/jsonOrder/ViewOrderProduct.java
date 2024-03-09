package com.van.lily.model.order.front.view.order.jsonOrder;

import com.van.lily.commons.core.annotations.TableJsonField;
import com.van.lily.model.order.front.view.order.jsonOrder.jsonProduct.ViewOrderProductRequire;
import com.van.lily.model.product.front.view.cake.jsonCake.ViewCakeSize;
import com.van.lily.model.product.front.view.cake.jsonCake.ViewCakeVariation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@ApiModel(value = "订单产品视图")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewOrderProduct {

    @ApiModelProperty("提交日期")
    private Date published;

    @ApiModelProperty("产品 ID")
    private Long id;
    @ApiModelProperty("产品 编号")
    private String number;
    @ApiModelProperty("产品 名称")
    private String name;
    @ApiModelProperty("购买数量")
    private Short quantity;

    @ApiModelProperty("是否是主蛋糕产品")
    private Short is_main_cake;

    @ApiModelProperty("产品保质期（小时）")
    private Integer hour_live;
    @ApiModelProperty("新鲜食用期（小时）")
    private Integer hour_fresh;

    @ApiModelProperty("选中的尺寸列表")
    private ViewCakeSize size;
    @ApiModelProperty("选中的样式列表")
    private List<ViewCakeVariation> variations;
    @ApiModelProperty("选中的要求列表")
    private List<ViewOrderProductRequire> requires;

    @ApiModelProperty("最新售价")
    private BigDecimal price_newest_selling;
    @ApiModelProperty("原售价")
    private BigDecimal price_origin_selling;
}
