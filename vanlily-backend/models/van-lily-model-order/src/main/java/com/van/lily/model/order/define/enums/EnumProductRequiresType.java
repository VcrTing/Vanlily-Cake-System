package com.van.lily.model.order.define.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum EnumProductRequiresType implements IEnum<String> {

    birthday_text("birthday_text"), // 生日牌字粒
    lacework_text("lacework_text"), // 花边文字
    hope_text("hope_text"), // 生日寄语
    other("other"); // 其他时间

    /**
     * 默认状态
     */
    public static EnumProductRequiresType def() { return other; }

    EnumProductRequiresType(String v) { this.v = v; }
    private final String v;
    @Override
    public String getValue() { return this.v; }
}