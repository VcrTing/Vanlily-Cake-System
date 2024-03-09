package com.van.lily.model.order.front.form.delivery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "自取的表單")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormDeliverySelfGet {

    @ApiModelProperty("自提订单号")
    private String uuid;
    @ApiModelProperty("自提用户电话号码")
    private String phone;
    @ApiModelProperty("自提流水号")
    private String article_number;
}
