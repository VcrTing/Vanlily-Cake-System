package com.van.lily.model.order.front.form.delivery;

import com.van.lily.model.order.front.form.delivery.jsonMan.FormDeliveryMan;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@ApiModel(value = "配送的表單")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormDeliverySender {

    @ApiModelProperty("对接送货员")
    private FormDeliveryMan delivery_man;

    @ApiModelProperty("订单 ID")
    private Long order_id;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("预订送达时间")
    private Date reserve_arrive_time;

}
