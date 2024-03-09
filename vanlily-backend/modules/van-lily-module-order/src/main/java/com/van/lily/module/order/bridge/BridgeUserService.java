package com.van.lily.module.order.bridge;

import com.van.lily.model.system.entity.User;

public interface BridgeUserService {
    /**
    * 根据电话号码获取客人
    */
    User oneByPhone(String phone);

    /**
     * 存储入缓存内
     */
    void saveToCache(User user);
}
