package com.van.lily.model.order.front.form.filling.jsonOrder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "订单备注数据")
public class FormOrderRemark {

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("所属类型")
    private String type_text;
}
