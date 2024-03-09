package com.van.lily.model.order.front.form.filling.jsonDelivery;

import com.van.lily.model.order.define.enums.EnumDeliveryTime;
import com.van.lily.model.order.define.enums.EnumOrderTakeType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "订单配送信息")
public class FormOrderDelivery {

    @ApiModelProperty("订单取货类型")
    private EnumOrderTakeType type_take;

    @ApiModelProperty("收货人姓名")
    private String receive_name;
    @ApiModelProperty("收货电话")
    private String receive_phone;
    @ApiModelProperty("收货内容一")
    private String receive_content_1;
    @ApiModelProperty("收货内容二")
    private String receive_content_2;
    @ApiModelProperty("收货备注")
    private String receive_remark;

    @ApiModelProperty("预订送达时间")
    private Date reserve_arrive_time;
    @ApiModelProperty("预订送货时间段开始")
    private Date reserve_delivery_time_start;
    @ApiModelProperty("预订送货时间段结束")
    private Date reserve_delivery_time_end;

}
