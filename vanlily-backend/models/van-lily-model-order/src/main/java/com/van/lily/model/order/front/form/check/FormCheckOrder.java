package com.van.lily.model.order.front.form.check;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "提交清单检查")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormCheckOrder {

    @ApiModelProperty("排序值")
    private Short index;

    @ApiModelProperty("出货检查")
    private Short is_out_stock;

    @ApiModelProperty("完成检查")
    private Short is_complete;
}
