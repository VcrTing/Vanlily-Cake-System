package com.van.lily.model.order.front.view.order;

import com.van.lily.commons.core.annotations.TableJsonField;
import com.van.lily.model.order.define.enums.*;
import com.van.lily.model.order.entity.Delivery;
import com.van.lily.model.order.front.view.order.jsonMan.ViewOrderCharge;
import com.van.lily.model.order.front.view.order.jsonOrder.ViewOrderDiscount;
import com.van.lily.model.order.front.view.order.jsonOrder.ViewOrderPayment;
import com.van.lily.model.order.front.view.order.jsonOrder.ViewOrderProduct;
import com.van.lily.model.order.front.view.order.jsonOrder.ViewOrderRemark;
import com.van.lily.model.order.front.view.order.jsonRefund.ViewOrderRefund;
import com.van.lily.model.system.entity.Customer;
import com.van.lily.model.system.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@ApiModel(value = "订单视图")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewOrder {

    private Long id;
    private Date published;

    @ApiModelProperty("订单内部编号")
    private String uuid;
    @ApiModelProperty("订单取货编号")
    private String article_number;
    @ApiModelProperty("订单来源记录")
    private EnumOrderFrom be_from;
    @ApiModelProperty("订单取货类型")
    private EnumOrderTakeType type_take;

    @ApiModelProperty("数据更新日期")
    private Date date_update;
    @ApiModelProperty("订单结束日期")
    private Date date_over;
    @ApiModelProperty("付款时间")
    private Date date_paid;

    @ApiModelProperty("配送状态")
    private EnumOrderSendStatus status_send;
    @ApiModelProperty("出单状态")
    private EnumOrderMakeStatus status_make;
    @ApiModelProperty("全局生命状态")
    private EnumOrderStatus status_life;

    @ApiModelProperty("订单是否已完善")
    private Short is_fill;
    @ApiModelProperty("订单是否已支付")
    private Short is_paid;
    @ApiModelProperty("是否是预订订单")
    private Short is_ahead;
    @ApiModelProperty("是否结束的订单")
    private Short is_over;

    @ApiModelProperty("清單是否檢查完成")
    private Short is_check_complete;
    @ApiModelProperty("清单检查结束时间")
    private Date checking_end_time;

    @ApiModelProperty("订单全部折扣统计总金额")
    private BigDecimal price_discount;
    @ApiModelProperty("系统生成的订单总价格")
    private BigDecimal price_generate;
    @ApiModelProperty("被客人支付的总价格")
    private BigDecimal price_paid;

    @ApiModelProperty("购单客户")
    private Long customer_id;
    @ApiModelProperty("收银员")
    private Long cashier_id;

    @ApiModelProperty("下单的负责人信息")
    @TableJsonField
    private ViewOrderCharge charge_checkout;

    @ApiModelProperty("订单全部备注")
    @TableJsonField()
    private List<ViewOrderRemark> remarks;
    @ApiModelProperty("下单产品信息")
    @TableJsonField()
    private List<ViewOrderProduct> products;
    @ApiModelProperty("支付信息")
    @TableJsonField()
    private List<ViewOrderPayment> payments;
    @ApiModelProperty("折扣信息")
    @TableJsonField()
    private List<ViewOrderDiscount> discounts;

    @ApiModelProperty("退單信息")
    @TableJsonField
    private ViewOrderRefund refund;

    @ApiModelProperty("下單客人")
    private Customer customer;
    @ApiModelProperty("收銀員")
    private User cashier;
    @ApiModelProperty("配送信息")
    private Delivery delivery;
}
