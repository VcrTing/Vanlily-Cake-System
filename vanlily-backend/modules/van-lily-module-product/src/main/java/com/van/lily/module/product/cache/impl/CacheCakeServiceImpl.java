package com.van.lily.module.product.cache.impl;

import com.van.lily.commons.keys.cache.KeyProductConstants;
import com.van.lily.framework.core.tools.RedisTool;
import com.van.lily.model.product.entity.Cake;
import com.van.lily.model.product.front.view.cake.ViewCake;
import com.van.lily.model.product.mapper.entity.CakeMapper;
import com.van.lily.module.product.cache.CacheCakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CacheCakeServiceImpl implements CacheCakeService {

    @Autowired
    RedisTool redisTool;

    @Autowired
    CakeMapper cakeMapper;

    // 获取 DAO CAKE
    public ViewCake daoOne(Long orderID) { return null; }

    // 存入缓存
    public void saveCake(Cake cake) {
        if (cake != null && cake.getId() != null)
            redisTool.setObject(KeyProductConstants.CAKE_BY_ID + cake.getId(), cake);
    }
}
