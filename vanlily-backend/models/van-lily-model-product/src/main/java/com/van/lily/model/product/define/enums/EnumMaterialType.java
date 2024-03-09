package com.van.lily.model.product.define.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum EnumMaterialType implements IEnum<String> {

    long_time_solid("long_time_solid"), // 長期儲存的固體
    long_time_fluid("long_time_fluid"), // 長期儲存的流體

    middle_time_solid("middle_time_solid"), // 中期儲存的固體
    middle_time_fluid("middle_time_fluid"), // 中期儲存的流體

    short_time_solid("short_time_solid"), // 短期儲存的固體
    short_time_fluid("short_time_fluid"); // 短期儲存的流體

    EnumMaterialType(String v) { this.v = v; }
    private final String v;
    @Override
    public String getValue() { return this.v; }
}