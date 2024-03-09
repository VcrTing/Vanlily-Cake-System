package com.van.lily.model.order.front.form.delivery.jsonMan;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "自取的送货员")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormDeliveryMan {
    @ApiModelProperty("送货员编号，如果有的话")
    private String number;
    @ApiModelProperty("送货员名字")
    private String name;
    @ApiModelProperty("送货员联络电话")
    private String phone;
    @ApiModelProperty("送货员备注")
    private String remark;
    @ApiModelProperty("是否是本店自营送货员")
    private String is_self_support;
}
