package com.van.lily.module.product.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.van.lily.commons.core.AjaxResult;
import com.van.lily.commons.core.AjaxResultUtil;
import com.van.lily.model.product.entity.Cake;
import com.van.lily.model.product.front.form.cake.FormCake;
import com.van.lily.model.product.mapper.entity.CakeMapper;
import com.van.lily.module.product.cache.CacheCakeService;
import com.van.lily.module.product.service.base.CakeService;
import com.van.lily.module.product.service.tool.impl.CakeToolServiceImpl;
import com.van.lily.module.product.transfer.impl.TransferCakeServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CakeServiceImpl extends ServiceImpl<CakeMapper, Cake> implements CakeService {

    @Autowired
    CacheCakeService cacheCakeService;

    @Autowired
    CakeToolServiceImpl toolService;

    @Autowired
    TransferCakeServiceImpl transferCakeService;

    public <T> AjaxResult pag(T param) {
        return AjaxResultUtil.success(this.list());
    }

    public AjaxResult one(Long id) {
        return null;
    }

    /**
    * 新增
    */
    public <T> AjaxResult pos(T form) {
        FormCake formCake = (FormCake) form;
        // 构建数据
        Cake cake = transferCakeService.formToEntity(formCake);
        System.out.println(cake);
        // 查重复
        Cake same = toolService.oneByNumber(cake.getNumber());
        if (same != null) return AjaxResultUtil.error("檢測到相同的蛋糕編號，已存在相同編號的蛋糕數據");
        // 存入數據庫
        boolean isOK = this.save(cake);
        if (!isOK) return AjaxResultUtil.error("因網絡波動，無法新增該蛋糕數據");
        log.debug("新增的蛋糕 ID = " + cake.getId());
        // 不在这里处理限定售卖的问题
        // 存入緩存
        cacheCakeService.saveCake(cake);
        // 返回
        return AjaxResultUtil.success(cake);
    }

    public <T> AjaxResult upd(Long id, T form) {
        return null;
    }

}
