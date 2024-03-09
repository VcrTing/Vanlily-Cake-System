package com.van.lily.model.order.front.form.filling;

import com.van.lily.model.order.front.form.filling.jsonDelivery.FormOrderDelivery;
import com.van.lily.model.order.front.form.filling.jsonOrder.FormChargeSimply;
import com.van.lily.model.order.front.form.filling.jsonProduct.FormFillingOrderProduct;
import com.van.lily.model.order.front.form.filling.jsonOrder.FormOrderDiscount;
import com.van.lily.model.order.front.form.filling.jsonOrder.FormOrderRemark;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "填充订单信息")
public class FormFillingOrder {

    @ApiModelProperty("訂單 ID")
    private Long id;

    @ApiModelProperty("订单负责人")
    private FormChargeSimply charge;

    @ApiModelProperty("订单折扣")
    private List<FormOrderDiscount> discounts;

    @ApiModelProperty("订单备注")
    private List<FormOrderRemark> remarks;

    @ApiModelProperty("订单产品列表")
    private List<FormFillingOrderProduct> products;

    @ApiModelProperty("送货信息")
    private FormOrderDelivery delivery;
}
