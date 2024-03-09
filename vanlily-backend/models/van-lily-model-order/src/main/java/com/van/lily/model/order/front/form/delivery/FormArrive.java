package com.van.lily.model.order.front.form.delivery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@ApiModel(value = "送达")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormArrive {

    @ApiModelProperty("送达时间")
    private Date arrive_time;

    @ApiModelProperty("送达备注")
    private String arrive_remark;
}
