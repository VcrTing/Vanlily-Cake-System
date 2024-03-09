package com.van.lily.model.order.front.view.remind;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "提醒的承载数据数据")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewRemindData {

    @ApiModelProperty("所属订单 ID")
    private Long order_id;

    @ApiModelProperty("所属配送 ID")
    private Long delivery_id;
}
