package com.van.lily.access.define.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum EnumSecurityRoleType implements IEnum<String> {
    customer("customer"), // 客人
    employee("employee"), // 系統用戶，員工
    administrator("administrator"), // 系統用戶，總管理員
    other("other"); // 其他

    EnumSecurityRoleType(String v) { this.v = v; }
    private final String v;
    @Override
    public String getValue() { return this.v; }
}