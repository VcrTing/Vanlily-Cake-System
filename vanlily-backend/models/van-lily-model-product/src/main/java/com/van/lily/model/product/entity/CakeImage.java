package com.van.lily.model.product.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.van.lily.commons.define.data.SqlFieldConstants;
import com.van.lily.model.product.define.ProductTableNameDefine;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@ApiModel(value = "蛋糕图片")
@TableName(ProductTableNameDefine.CAKE_IMAGE)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CakeImage {

    @TableLogic(value = SqlFieldConstants.LIVE, delval = SqlFieldConstants.DIE)
    private Short live;
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private Date published;

    @ApiModelProperty("图片名称（含后缀）")
    private String name;

    @ApiModelProperty("观看顺序")
    private Short index;

    @ApiModelProperty("是否是精选图片")
    private Short is_awesome;

    @ApiModelProperty("所属蛋糕")
    private Long cake_id;
}
