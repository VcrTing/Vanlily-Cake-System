package com.van.lily.model.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.van.lily.commons.core.annotations.TableJsonField;
import com.van.lily.commons.define.data.SqlFieldConstants;
import com.van.lily.model.order.define.OrderTableNameDefine;
import com.van.lily.model.order.define.enums.EnumOrderBrokenType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value = "坏掉的订单")
@TableName(OrderTableNameDefine.ORDER_BROKEN)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrokenOrder {

    @TableLogic(value = SqlFieldConstants.LIVE, delval = SqlFieldConstants.DIE)
    private Short live;
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private Date published;

    @ApiModelProperty("所属 订单 ID")
    private Long order_id;
    @ApiModelProperty("所属 订单 UUID")
    private String order_uuid;
    @ApiModelProperty("所属 订单 是否已付款")
    private Short order_is_paid;
    @ApiModelProperty("坏单备注")
    private String remark;
    @ApiModelProperty("坏单具体时间")
    private Date date_broken;
    @ApiModelProperty("意外类型")
    private EnumOrderBrokenType type_broken;
    @ApiModelProperty("参与者")
    @TableJsonField
    private String participant;
    @ApiModelProperty("坏单描述")
    @TableJsonField
    private String content;
    @ApiModelProperty("订单原本金额")
    private BigDecimal price_order_origin;
    @ApiModelProperty("保底金额损失")
    private BigDecimal price_loss;
}
