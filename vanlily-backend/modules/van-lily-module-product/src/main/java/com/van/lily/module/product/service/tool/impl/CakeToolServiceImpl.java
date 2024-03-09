package com.van.lily.module.product.service.tool.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.van.lily.model.product.entity.Cake;
import com.van.lily.model.product.mapper.entity.CakeMapper;
import com.van.lily.module.product.cache.CacheCakeService;
import com.van.lily.module.product.service.tool.CakeToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CakeToolServiceImpl implements CakeToolService {

    @Autowired
    CakeMapper mapper;

    @Autowired
    CacheCakeService cacheCakeService;

    public Cake oneByNumber(String number) {
        LambdaQueryWrapper<Cake> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Cake::getNumber, number);
        Cake cake = mapper.selectOne(queryWrapper);
        // 嘗試存入庫存
        cacheCakeService.saveCake(cake);
        return cake;
    }
}
