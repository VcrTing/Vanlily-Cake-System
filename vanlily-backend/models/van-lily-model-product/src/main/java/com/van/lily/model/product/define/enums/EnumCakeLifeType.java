package com.van.lily.model.product.define.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum EnumCakeLifeType implements IEnum<String> {

    fresh("fresh"), // 需要最新鲜的
    shortly("shortly"), // 短期储存
    long_time("long_time"); // 稍微可以长期储存

    EnumCakeLifeType(String v) { this.v = v; }
    private final String v;
    @Override
    public String getValue() { return this.v; }
}