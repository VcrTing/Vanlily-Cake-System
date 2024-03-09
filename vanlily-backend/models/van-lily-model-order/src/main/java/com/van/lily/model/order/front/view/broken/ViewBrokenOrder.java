package com.van.lily.model.order.front.view.broken;

import com.van.lily.commons.core.annotations.TableJsonField;
import com.van.lily.model.order.define.enums.EnumOrderBrokenType;
import com.van.lily.model.order.entity.Order;
import com.van.lily.model.order.front.view.broken.jsonInner.ViewOrderBrokenContent;
import com.van.lily.model.order.front.view.broken.jsonInner.ViewOrderBrokenParticipant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value = "坏单视图")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewBrokenOrder {

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
    @ApiModelProperty("坏单描述")
    @TableJsonField
    private ViewOrderBrokenContent content;
    @ApiModelProperty("参与者")
    @TableJsonField
    private ViewOrderBrokenParticipant participant;
    @ApiModelProperty("订单原本金额")
    private BigDecimal price_order_origin;
    @ApiModelProperty("保底金额损失")
    private BigDecimal price_loss;
}
