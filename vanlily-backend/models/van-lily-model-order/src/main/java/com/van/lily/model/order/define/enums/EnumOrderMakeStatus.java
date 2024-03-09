package com.van.lily.model.order.define.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum EnumOrderMakeStatus implements IEnum<String> {

    waiting("waiting"), // 等待中
    make("make"), // 制作中
    // made("made"), // 蛋糕制作完成，待装盒
    packaged("packaged"); // 装盒完成，待配送


    /**
     * 默认状态
     */
    public static EnumOrderMakeStatus def() { return waiting; }
    /**
    * 還未開始製作
    */
    public static Boolean isInWaiting(EnumOrderMakeStatus status) { return status != null && status.equals(waiting); }

    EnumOrderMakeStatus(String v) { this.v = v; }
    private final String v;
    @Override
    public String getValue() { return this.v; }
}