package com.van.lily.model.order.define.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum EnumOrderStatus implements IEnum<String> {

    working("working"), // 处于工作状态
    canceled("canceled"), // 已取消
    refunded("refunded"), // 已退单
    finish("finish"), // 已完成
    freeze("freeze"); // 静态冰冻状态

    /**
    * 默认状态
    */
    public static EnumOrderStatus def() { return working; }
    /**
    * 是否工作状态
    */
    public static Boolean isInWorking(EnumOrderStatus status) { return status != null && status.equals(working); }

    EnumOrderStatus(String v) { this.v = v; }
    private final String v;
    @Override
    public String getValue() { return this.v; }
}