package com.van.lily.model.system.define.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum EnumCustomerFrom implements IEnum<String> {

    web("web"), //
    nearby("nearby"), //
    distance("distance"), //
    video("video"), //
    article("article"), //
    recommend("recommend"), //
    tiktok("tiktok"), // 抖音
    other("other"), // 其他未知来源
    advert("advert"); // 广告

    /**
     * 默认状态
     */
    public static EnumCustomerFrom def() { return web; }

    EnumCustomerFrom(String v) { this.v = v; }
    private final String v;
    @Override
    public String getValue() { return this.v; }
}