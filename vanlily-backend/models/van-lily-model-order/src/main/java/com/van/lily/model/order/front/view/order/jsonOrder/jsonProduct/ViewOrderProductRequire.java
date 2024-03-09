package com.van.lily.model.order.front.view.order.jsonOrder.jsonProduct;

import com.van.lily.model.order.define.enums.EnumProductRequiresType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@ApiModel(value = "订单产品要求视图")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewOrderProductRequire {

    @ApiModelProperty("提交日期")
    private Date published;

    @ApiModelProperty("产品备注内容")
    private String content;
    @ApiModelProperty("产品备注类型文字")
    private String type_text;
    @ApiModelProperty("产品备注类型")
    private EnumProductRequiresType typed;
}
