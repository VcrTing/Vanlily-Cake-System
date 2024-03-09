package com.van.lily.module.order.cache.impl;

import com.van.lily.commons.core.exception.QCloudException;
import com.van.lily.commons.core.exception.QLogicException;
import com.van.lily.commons.keys.cache.KeyOrderConstants;
import com.van.lily.framework.core.tools.RedisTool;
import com.van.lily.model.order.entity.Delivery;
import com.van.lily.model.order.mapper.entity.DeliveryMapper;
import com.van.lily.model.order.transfer.DeliveryTransfer;
import com.van.lily.module.order.cache.CacheDeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CacheDeliveryServiceImpl implements CacheDeliveryService {

    @Autowired
    RedisTool redisTool;

    @Autowired
    DeliveryMapper mapper;

    /**
    * 获取 ONE
    */
    public Delivery one(Long id) {
        String key = KeyOrderConstants.DELIVERY_BY_ID + id;
        Delivery res = redisTool.getObject(key);
        if (res == null) {
            res = mapper.selectById(id);
            if (res != null) {
                saveToCache(res);
                redisTool.expire(key);
            }
        } else { redisTool.expire(key); }
        if (DeliveryTransfer.isBad(res)) throw new QCloudException("配送數據為空，請檢查該訂單是否存在。");
        return res;
    }

    /**
    * 修改
    */
    public Delivery upd(Delivery entity) {
        if (DeliveryTransfer.isBad(entity)) throw new QCloudException("配送數據為空，請檢查該訂單是否存在。");
        entity.setDate_update(new Date());
        int res = mapper.updateById(entity);
        if (res > 0) { saveToCache(entity); return entity; }
        throw new QLogicException("因網絡波動，無法同步完善訂單數據，本次配送信息完善失敗");
    }

    // 存入緩存
    public void saveToCache(Delivery src) {
        if (DeliveryTransfer.isGood(src)) {
            String key = KeyOrderConstants.DELIVERY_BY_ID + src.getId();
            redisTool.setObject(key, src);
        }
    }
}
