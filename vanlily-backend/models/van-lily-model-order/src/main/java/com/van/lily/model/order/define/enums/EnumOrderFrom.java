package com.van.lily.model.order.define.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum EnumOrderFrom implements IEnum<String> {

    web("web"), // 官方网站
    shop("shop"), // 店内直购
    phone("phone"), // 电话预订
    wx("wx"), // 微信
    twitter("twitter"), // twitter
    facebook("facebook"), // facebook
    tiktok("tiktok"), // 抖音
    other("other"), // 其他来源
    whatsapp("whatsapp"); // whatsapp

    /**
     * 默认状态
     */
    public static EnumOrderFrom def() { return web; }

    EnumOrderFrom(String v) { this.v = v; }
    private final String v;
    @Override
    public String getValue() { return this.v; }
}