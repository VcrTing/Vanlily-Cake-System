package com.van.lily.model.order.front.form.filling.jsonProduct;

import com.van.lily.model.order.front.form.filling.jsonProduct.inner.FormFillingProductRequire;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class FormFillingOrderProduct {

    @ApiModelProperty("产品 ID")
    private Long id;
    @ApiModelProperty("购买数量")
    private Short quantity;

    @ApiModelProperty("標識蛋糕 尺寸")
    private Short g;
    @ApiModelProperty("样式选择结果")
    private List<String> variations_article_number;

    @ApiModelProperty("产品最终确认备注")
    private List<FormFillingProductRequire> requires;
}
