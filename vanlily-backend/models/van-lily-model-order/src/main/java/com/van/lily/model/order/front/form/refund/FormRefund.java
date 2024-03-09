package com.van.lily.model.order.front.form.refund;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "退单表单")
public class FormRefund {

    @ApiModelProperty("訂單 ID")
    private Long order_id;

    @ApiModelProperty("退钱负责人 ID")
    private Long charge_id;
    @ApiModelProperty("退钱负责人 名稱")
    private Long charge_name;
    @ApiModelProperty("退钱负责人 電話")
    private Long charge_phone;

    @ApiModelProperty("收款人账号")
    private String refund_account;
    @ApiModelProperty("收款人电话")
    private String refund_phone;

    @ApiModelProperty("退单备注")
    private String refund_remark;

    @ApiModelProperty("退钱渠道")
    private String refund_channel;
    @ApiModelProperty("退钱金额")
    private BigDecimal refund_price;

}
