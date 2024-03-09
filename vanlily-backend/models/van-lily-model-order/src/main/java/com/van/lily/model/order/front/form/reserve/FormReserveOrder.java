package com.van.lily.model.order.front.form.reserve;

import com.van.lily.model.order.define.enums.EnumOrderFrom;
import com.van.lily.model.order.front.form.reserve.jsonOrder.FormCustomerSimply;
import com.van.lily.model.order.front.form.reserve.jsonOrder.FormReserveOrderProduct;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 预约下单
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "预约下单的表单")
public class FormReserveOrder {

    @ApiModelProperty("客人信息")
    private FormCustomerSimply customer;

    @ApiModelProperty("收银人员 ID")
    private Long cashier_id;

    @ApiModelProperty("客人挑选的产品，和产品数量")
    private List<FormReserveOrderProduct> products;

    @ApiModelProperty("订单，底线时间")
    private Date date_finally;

    @ApiModelProperty("是否是长时间预约订单")
    private Short is_ahead;

    @ApiModelProperty("订单来源记录")
    private EnumOrderFrom be_from;
}
