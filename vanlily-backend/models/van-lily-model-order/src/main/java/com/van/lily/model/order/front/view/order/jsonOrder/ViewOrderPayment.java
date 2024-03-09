package com.van.lily.model.order.front.view.order.jsonOrder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value = "单条支付信息")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewOrderPayment {

    @ApiModelProperty("支付方式展示文本")
    private String way_text;
    @ApiModelProperty("支付方式代号")
    private String way;
    @ApiModelProperty("支付时间")
    private Date pay_time;
    @ApiModelProperty("支付价格")
    private BigDecimal price;
    @ApiModelProperty("启用该支付信息")
    private Boolean is_active;
}
