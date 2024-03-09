package com.van.lily.model.order.define.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum EnumDeliveryTime implements IEnum<String> {

    am("10:00-12:00"), // 早上
    noon("12:00-14:00"), // 中午
    pm("14:00-17:00"), // 下午
    nightfall("17:00-19:00"), // 傍晚
    night("19:00-22:00"), // 夜晚
    other("24:00-10:00"); // 其他时间

    /**
     * 默认状态
     */
    public static EnumDeliveryTime def() { return pm; }

    EnumDeliveryTime(String v) { this.v = v; }
    private final String v;
    @Override
    public String getValue() { return this.v; }
}