package com.van.lily.model.order.define.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum EnumPersonBelongType implements IEnum<String> {

    leader("leader"), // 領導級別
    staff("staff"), // 員工
    customer("customer"), // 客戶
    no_staff("no_staff"), // 非店內人員
    other("other"); // 其他


    EnumPersonBelongType(String v) { this.v = v; }
    private final String v;
    @Override
    public String getValue() { return this.v; }
}