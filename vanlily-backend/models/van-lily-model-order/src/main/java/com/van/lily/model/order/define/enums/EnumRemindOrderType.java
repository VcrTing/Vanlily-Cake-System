package com.van.lily.model.order.define.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum EnumRemindOrderType implements IEnum<String> {

    pay_not_yet_for_customer("pay_not_yet_for_customer"), // 还未付款
    pay_not_yet_for_cashier("pay_not_yet_for_cashier"), // 还未付款
    paid_for_cashier("paid_for_cashier"), // 已付款，流水号已生成
    paid_for_customer("paid_for_customer"), // 已付款，提前将蛋糕做好

    packaged_for_customer("packaged_for_customer"), // 已打包，可以自提
    packaged_for_cashier("packaged_for_cashier"); // 快到配送时间，请留意配送

    EnumRemindOrderType(String v) { this.v = v; }
    private final String v;
    @Override
    public String getValue() { return this.v; }
}