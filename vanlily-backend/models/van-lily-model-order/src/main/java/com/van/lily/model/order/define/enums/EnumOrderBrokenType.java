package com.van.lily.model.order.define.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum EnumOrderBrokenType implements IEnum<String> {

    force_refund("force_refund"), // 强制退单
    delivery_accident("delivery_accident"), // 配送意外
    staff_opera_accident("staff_opera_accident"), // 员工导致
    sanitation_accident("sanitation_accident"), // 卫生意外
    other("other"); // 其他


    EnumOrderBrokenType(String v) { this.v = v; }
    private final String v;
    @Override
    public String getValue() { return this.v; }
}