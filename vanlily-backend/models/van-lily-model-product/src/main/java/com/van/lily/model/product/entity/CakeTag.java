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

@ApiModel(value = "蛋糕标签")
@TableName(ProductTableNameDefine.CAKE_TAG)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CakeTag {

    @TableLogic(value = SqlFieldConstants.LIVE, delval = SqlFieldConstants.DIE)
    private Short live;
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private Date published;

    @ApiModelProperty("标签名称")
    private String name;
    @ApiModelProperty("标签标识（全英文）")
    private String code;
    @ApiModelProperty("标签存储标识（经过系统处理）")
    private String code_text;
}
