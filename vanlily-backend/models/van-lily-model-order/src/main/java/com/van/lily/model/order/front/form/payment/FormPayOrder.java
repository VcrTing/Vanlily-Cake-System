package com.van.lily.model.order.front.form.payment;

import com.van.lily.model.order.front.form.payment.jsonOrder.FormOrderPayment;
import com.van.lily.model.order.front.view.order.jsonMan.ViewOrderCharge;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "订单支付")
public class FormPayOrder {

    @ApiModelProperty("訂單 ID")
    private Long id;
    @ApiModelProperty("訂單付款負責人")
    private ViewOrderCharge charge_checkout;
    @ApiModelProperty("支付信息列表")
    private List<FormOrderPayment> payments;
}
