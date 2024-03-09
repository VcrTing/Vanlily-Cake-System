package com.van.lily.module.order.bridge;

import com.van.lily.model.system.entity.Customer;

public interface BridgeCustomerService {
    /**
    * 根据电话号码获取客人
    */
    Customer oneByPhone(String phone);

    /**
    * 创建一个默认客人
    */
    Customer posSimply(String phone, String name);

    /**
     * 存储入缓存内
     */
    void saveToCache(Customer customer);

    /**
    * 根据 客户 电话号码，获取客户，若未获取到，则进行新增
    */
    Customer oneOrCreat(String phone, String name);
}
