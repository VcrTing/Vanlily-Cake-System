package com.van.lily.model.product.front.view.cake.jsonCake;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewCakeSize {

    @ApiModelProperty("尺寸蛋糕编号")
    private String number;
    @ApiModelProperty("尺寸备注")
    private String remark;
    @ApiModelProperty("尺寸表述")
    private String text;
    @ApiModelProperty("克")
    private Short g;
    @ApiModelProperty("直径")
    private String diameter;

    @ApiModelProperty("最新售價")
    private BigDecimal price_newest_selling;
    @ApiModelProperty("原價")
    private BigDecimal price_origin_selling;
}
