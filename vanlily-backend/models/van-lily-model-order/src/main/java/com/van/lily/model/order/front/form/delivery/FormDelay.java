package com.van.lily.model.order.front.form.delivery;

import com.van.lily.commons.core.annotations.TableJsonField;
import com.van.lily.model.order.define.enums.EnumOrderTakeType;
import com.van.lily.model.order.front.view.delivery.jsonDelay.ViewDeliveryDelay;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@ApiModel(value = "延迟送货")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormDelay {

    @ApiModelProperty("提交时间")
    private Date published;
    @ApiModelProperty("本次延迟备注")
    private Short remark;
    @ApiModelProperty("延迟次数")
    private Short index;

    @ApiModelProperty("延迟取货方式")
    private EnumOrderTakeType takeType;

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

    @ApiModelProperty("预订延迟送货时间段开始")
    private Date reserve_delivery_time_start;
    @ApiModelProperty("预订延迟送货时间段结束")
    private Date reserve_delivery_time_end;
}
