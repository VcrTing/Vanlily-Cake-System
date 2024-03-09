package com.van.lily.model.product.front.view.cake.jsonCake;

import com.van.lily.model.product.front.view.cake.jsonCake.inner.ViewCakeVariationItem;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewCakeVariation {

    @ApiModelProperty("样式选项")
    private List<ViewCakeVariationItem> items;
    @ApiModelProperty("样式标签名称")
    private String text;
    @ApiModelProperty("样式标识")
    private String code;
    @ApiModelProperty("是否开启")
    private Short live;
    @ApiModelProperty("排序系数")
    private Short index;
    @ApiModelProperty("提交日期")
    private Date published;

    @ApiModelProperty("封面")
    private String cover;
}
