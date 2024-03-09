package com.van.lily.model.order.front.form.reserve.jsonOrder;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FormCustomerSimply {

    @ApiModelProperty("客人标识电话")
    private String phone;
    @ApiModelProperty("客人称呼")
    private String name;
}
