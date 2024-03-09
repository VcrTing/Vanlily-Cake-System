package com.van.lily.model.product.front.view.cake.jsonCake;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewCakeTag {

    @ApiModelProperty("标签名称，中文")
    private String name_zh;
    @ApiModelProperty("标签名称，英文")
    private String name_en;
    @ApiModelProperty("标签名称，扩展文字")
    private String name_other;
    @ApiModelProperty("标签用来方便搜索用的标识组合")
    private String code_save;
    @ApiModelProperty("提交日期")
    private Date published;
}
