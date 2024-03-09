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

import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value = "坏掉的蛋糕")
@TableName(ProductTableNameDefine.CAKE_BROKEN)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CakeBroken {

    @TableLogic(value = SqlFieldConstants.LIVE, delval = SqlFieldConstants.DIE)
    private Short live;
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private Date published;

    @ApiModelProperty("坏货之后的补救措施描述")
    private String remark_remedy;

    @ApiModelProperty("坏货详细备注")
    private String remark;

    @ApiModelProperty("坏货简单描述")
    private String desc;

    @ApiModelProperty("坏货时间")
    private Date date;

    @ApiModelProperty("所属蛋糕款式")
    private String cake_id;

    @ApiModelProperty("所属的订单（有则填）")
    private String order_id;

    @ApiModelProperty("金额损失")
    private BigDecimal price_loss;

}
