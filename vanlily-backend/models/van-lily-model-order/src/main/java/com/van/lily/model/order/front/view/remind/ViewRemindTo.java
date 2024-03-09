package com.van.lily.model.order.front.view.remind;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "提醒的接收者")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewRemindTo {

    @ApiModelProperty("收银员 ID")
    private Long cashier_id;
    @ApiModelProperty("客人 ID")
    private Long member_id;
}
