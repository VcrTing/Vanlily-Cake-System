package com.van.lily.module.order.cache;

import com.van.lily.model.order.entity.Delivery;

public interface CacheDeliveryService {

    /**
    * 获取 one Delivery
    */
    Delivery one(Long id);

    /**
    * 修改 one Delivery
    */
    Delivery upd(Delivery entity);

}
