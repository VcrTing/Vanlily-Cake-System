package com.van.lily.model.order.define.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum EnumOrderPayStatus implements IEnum<String> {

    not("not"), // 待付款
    yes("yes"); // 已付款

    /**
     * 默认状态
     */
    public static EnumOrderPayStatus def() { return not; }
    /**
    * 是否處於已付款狀態
    */
    public static Boolean isInYes(EnumOrderPayStatus status) { return status != null && status.equals(EnumOrderPayStatus.yes); }

    EnumOrderPayStatus(String v) { this.v = v; }
    private final String v;
    @Override
    public String getValue() { return this.v; }
}