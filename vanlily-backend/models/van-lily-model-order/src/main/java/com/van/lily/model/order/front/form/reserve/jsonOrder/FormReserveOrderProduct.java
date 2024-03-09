package com.van.lily.model.order.front.form.reserve.jsonOrder;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class FormReserveOrderProduct {

    @ApiModelProperty("产品 ID")
    private Long id;

    @ApiModelProperty("標識蛋糕 尺寸")
    private Short g;

    @ApiModelProperty("样式选择结果")
    private List<String> variations_article_number;
}
