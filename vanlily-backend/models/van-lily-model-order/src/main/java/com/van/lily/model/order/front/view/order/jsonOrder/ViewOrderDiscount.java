package com.van.lily.model.order.front.view.order.jsonOrder;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewOrderDiscount {

    @ApiModelProperty("排序值")
    private Short index;
    @ApiModelProperty("折扣储存备注")
    private String remark;
    @ApiModelProperty("折扣名称")
    private String name;
    @ApiModelProperty("折扣类型文字")
    private String type_text;

    @ApiModelProperty("折扣展示值")
    private String discount_text;
    @ApiModelProperty("折扣储存数据")
    private BigDecimal discount;
    @ApiModelProperty("订单扣除的折扣金额，用于计算")
    private BigDecimal discount_price;
}
