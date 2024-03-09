package com.van.lily.model.product.front.view.cake.jsonCake;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewCakeImage {

    @ApiModelProperty("照片重要程度系数")
    private Short awesome;
    @ApiModelProperty("照片名字，xxx.jpg")
    private String name;
    @ApiModelProperty("照片网络地址，不包含域名")
    private String link_address;
    @ApiModelProperty("提交的日期")
    private Date published;
}
