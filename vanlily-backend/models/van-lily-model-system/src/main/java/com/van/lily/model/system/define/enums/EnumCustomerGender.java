package com.van.lily.model.system.define.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum EnumCustomerGender implements IEnum<String> {

    man("man"),
    woman("woman"),
    other("other"),
    secret("secret"); // 通常

    /**
     * 默认状态
     */
    public static EnumCustomerGender def() { return secret; }

    EnumCustomerGender(String v) { this.v = v; }
    private final String v;
    public String getValue() { return this.v; }
}
