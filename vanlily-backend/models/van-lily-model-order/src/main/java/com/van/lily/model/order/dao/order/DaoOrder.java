package com.van.lily.model.order.dao.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.van.lily.commons.core.annotations.TableJsonField;
import com.van.lily.commons.define.data.SqlFieldConstants;
import com.van.lily.model.order.define.enums.*;
import com.van.lily.model.order.entity.Delivery;
import com.van.lily.model.system.entity.Customer;
import com.van.lily.model.system.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DaoOrder {

    // @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private Date published;
    // private Integer version;

    private Long customer_id;
    private Long cashier_id;
    private Customer customer;
    private User cashier;
    private Delivery delivery;

    private String uuid;
    private String article_number;
    private EnumOrderFrom be_from;
    private EnumOrderTakeType type_take;

    // private Date date_update;
    private Date date_over;
    // private Date date_paid;

    private EnumOrderSendStatus status_send;
    private EnumOrderMakeStatus status_make;
    private EnumOrderStatus status_life;

    private Short is_fill;
    private Short is_paid;
    // private Short is_ahead;
    private Short is_over;

    // @TableJsonField
    // private String checking_list;
    private Short is_check_complete;
    private Date checking_end_time;
    // @TableJsonField
    // private String charge_checking;

    // private BigDecimal price_discount;
    private BigDecimal price_generate;
    private BigDecimal price_paid;

    @TableJsonField
    private String charge_checkout;

    @TableJsonField
    private String refund;
    @TableJsonField
    private String remarks;
    @TableJsonField
    private String products;
    @TableJsonField
    private String payments;
    @TableJsonField
    private String discounts;
}
