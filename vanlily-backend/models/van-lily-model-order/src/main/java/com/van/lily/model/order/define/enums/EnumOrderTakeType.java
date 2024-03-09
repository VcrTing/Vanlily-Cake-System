package com.van.lily.model.order.define.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum EnumOrderTakeType implements IEnum<String> {

    self_get("self_get"), // 自取
    delivery("delivery"), // 配送
    delivery_get("delivery_get"); // 配送后自取

    /**
    * 仅配送
    */
    public static boolean onlySend(EnumOrderTakeType v) {
        return v != null && v.equals(delivery);
    }

    EnumOrderTakeType(String v) { this.v = v; }
    private final String v;
    @Override
    public String getValue() { return this.v; }
}