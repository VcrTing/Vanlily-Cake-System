package com.van.lily.access.define.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum EnumSecurityUserEntityType implements IEnum<String> {

    customer("customer"), // 客人
    user("user"), // 系統用戶
    other("other"); // 其他

    public static EnumSecurityUserEntityType fromString(String v) {
        if (v.equals(user.v)) return user;
        else if (v.equals(customer.v)) return customer;
        else if (v.equals(other.v)) return other;
        return null;
    }

    EnumSecurityUserEntityType(String v) { this.v = v; }
    private final String v;
    @Override
    public String getValue() { return this.v; }
}