package com.van.lily.model.order.front.form.filling.jsonOrder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "订单负责人信息")
public class FormChargeSimply {

    @ApiModelProperty("内部人员 ID")
    private Long id;
    @ApiModelProperty("内部人员编号")
    private String number;
    @ApiModelProperty("联络电话")
    private String phone;
    @ApiModelProperty("姓名")
    private String name;
}
