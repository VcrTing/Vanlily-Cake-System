package com.van.lily.module.order.common;

public interface ApiRouter {

    String ID = "/{id}";
    String PID = "/{pid}";
    String ORDER = "orders";

    String ORDER_PRODUCT = "order/product";

    /**
    * 订单查询
    */
    String SYSTEM_ORDERS = "system/orders";
    String SYSTEM_ORDERS_OPERA_BOARD = "system/orders/opera_board";
    String SYSTEM_ORDERS_WORK_BOARD = "system/orders/work_board";
    String SYSTEM_ORDERS_SEARCH = "system/orders/search";
    String CUSTOMER_ORDERS = "customer/orders";

    /**
    * 坏掉的订单
    */
    String BROKEN_ORDER = "broken/orders";

    /**
    * 订单操作
    */
    String RESERVE = "order/reserve";
    String FILLING = "order/filling";
    String PAYMENT = "order/payment";
    String CHECKLIST = "order/checklist";
    String PACKAGE = "order/package";
    String REFUND_NORMAL = "refund/normal";
    String REFUND_FORCE = "refund/force";

    /**
    * 配送
    */
    String DELIVERIES = "deliveries";
    String DELIVERIES_BY_ORDER = "deliveries/by/order";
    String DELIVERY_SELF_GET = "delivery/self_get";
    String DELIVERY_SENDER = "delivery/sender";
    String DELIVERY_SENDER_GET = "delivery/sender_get";

    String ARRIVE = "delivery/arrive";
    String DELAY = "delivery/delay";
}
