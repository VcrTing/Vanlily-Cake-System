package com.van.lily.model.order.front.view.delivery;

import com.van.lily.commons.core.annotations.TableJsonField;
import com.van.lily.model.order.define.enums.EnumOrderTakeType;
import com.van.lily.model.order.entity.Order;
import com.van.lily.model.order.front.view.delivery.jsonDelay.ViewDeliveryDelay;
import com.van.lily.model.order.front.view.delivery.jsonMan.ViewDeliveryMan;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@ApiModel(value = "配送视图")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewDelivery {

    private Long id;
    private Date published;

    @ApiModelProperty("所属订单 ID")
    private Long order_id;
    @ApiModelProperty("订单")
    private Order order;

    @ApiModelProperty("数据更新日期")
    private Date date_update;
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

    @ApiModelProperty("预订送货时间段开始")
    private Date reserve_delivery_time_start;
    @ApiModelProperty("预订送货时间段结束")
    private Date reserve_delivery_time_end;
    @ApiModelProperty("预订送达时间")
    private Date reserve_arrive_time;
    @ApiModelProperty("最新预計送达时间")
    private Date newest_arrive_time;

    @ApiModelProperty("起送时间")
    private Date delivery_start_time;
    @ApiModelProperty("配送结束时间")
    private Date delivery_end_time;

    @ApiModelProperty("送達备注")
    private String arrive_remark;
    @ApiModelProperty("是否在配送")
    private Short is_in_delivery;
    @ApiModelProperty("是否送達")
    private Short is_arrive;
    @ApiModelProperty("是否结束")
    private Short is_over;
    @ApiModelProperty("配送员信息")
    @TableJsonField
    private ViewDeliveryMan delivery_man;

    @ApiModelProperty("是否意外")
    private Short is_accident;
    @ApiModelProperty("配送意外")
    @TableJsonField
    private String delivery_accident;

    @ApiModelProperty("是否延迟送货")
    private Short is_delay;
    @ApiModelProperty("延迟送货备注")
    private Short delay_remark;
    @ApiModelProperty("延迟送货信息")
    @TableJsonField
    private ViewDeliveryDelay delay;
}
