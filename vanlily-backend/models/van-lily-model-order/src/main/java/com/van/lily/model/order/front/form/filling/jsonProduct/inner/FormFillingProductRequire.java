package com.van.lily.model.order.front.form.filling.jsonProduct.inner;

import com.van.lily.model.order.define.enums.EnumProductRequiresType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "订单产品需求数据")
public class FormFillingProductRequire {

    @ApiModelProperty("产品备注内容")
    private String content;
    @ApiModelProperty("产品备注类型文字")
    private String type_text;
    @ApiModelProperty("产品备注类型")
    private EnumProductRequiresType typed;
}
