package com.van.lily.model.order.define.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum EnumRemindToType implements IEnum<String> {

    message("message"), // 短信
    email("email"), // 电邮
    all("all"), // 全部
    other("other"); // other

    EnumRemindToType(String v) { this.v = v; }
    private final String v;
    @Override
    public String getValue() { return this.v; }
}