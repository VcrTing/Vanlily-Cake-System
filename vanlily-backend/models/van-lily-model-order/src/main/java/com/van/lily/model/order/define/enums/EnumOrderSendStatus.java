package com.van.lily.model.order.define.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum EnumOrderSendStatus implements IEnum<String> {

    silence("silence"), // 沉默中
    connecting("connecting"), // 联络中
    delayed("delayed"), // 延迟送货
    sending("sending"), // 配送中

    delivered_ahead("delivered_ahead"), // 提前送达
    delivered_delay("delivered_delay"), // 延后送达
    delivered_normal("delivered_normal"); // 正常范围内送达

    public final static String TEXT_IN_STORE = "sending";
    public final static String TEXT_SENDING = "sending";
    public final static String TEXT_ARRIVE = "delivered";

    /**
    * 該訂單產品，是否在店舖內
    */
    public static Boolean isInStore(EnumOrderSendStatus status) {
        if (status == null) return true;
        return status == silence || status == connecting || status == delayed;
    }

    /**
    * 默认状态
    */
    public static EnumOrderSendStatus def() { return silence; }
    /**
    * 非沈默狀態
    */
    public static Boolean isNotSilence(EnumOrderSendStatus status) { return status != null && !status.equals(silence); }

    /**
    * 其他方法
    */
    private final String v;
    @Override
    public String getValue() { return this.v; }
    EnumOrderSendStatus(String v) { this.v = v; }
}