package com.van.lily.model.order.front.view.order.jsonMan;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewOrderCharge {
    @ApiModelProperty("負責人标识電郵")
    private String email;
    @ApiModelProperty("負責人标识电话")
    private String phone;
    @ApiModelProperty("負責人称呼")
    private String name;
    @ApiModelProperty("負責人ID")
    private String id;
}
