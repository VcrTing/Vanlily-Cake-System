package com.van.lily.module.order.service.delivery.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.van.lily.commons.core.AjaxResult;
import com.van.lily.commons.core.AjaxResultUtil;
import com.van.lily.commons.utils.PageHelperUtil;
import com.van.lily.commons.utils.qiong.QTypeUtil;
import com.van.lily.model.order.entity.Delivery;
import com.van.lily.model.order.entity.Order;
import com.van.lily.model.order.front.param.delivery.ParamDelivery;
import com.van.lily.model.order.mapper.entity.DeliveryMapper;
import com.van.lily.model.order.transfer.view.ViewDeliveryTransfer;
import com.van.lily.module.order.cache.impl.CacheDeliveryServiceImpl;
import com.van.lily.module.order.cache.impl.CacheOrderServiceImpl;
import com.van.lily.module.order.service.delivery.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DeliveryServiceImpl extends ServiceImpl<DeliveryMapper, Delivery> implements DeliveryService {

    @Autowired
    CacheDeliveryServiceImpl cacheDeliveryService;

    @Autowired
    CacheOrderServiceImpl cacheOrderService;

    @Autowired
    DeliveryMapper mapper;

    /**
    * 深度查询列表
    */
    public AjaxResult pag(ParamDelivery param) {
        PageHelperUtil.start(param);
        return PageHelperUtil.successResult(ViewDeliveryTransfer.transfers(this.list(param.queryWrapper())), param);
    }

    /**
    * 根据订单 ID 获取
    */
    public AjaxResult oneByOrder(Long orderID) {
        if (QTypeUtil.isNotLong(orderID)) return AjaxResultUtil.error("訂單 ID 異常，請檢查 ID");
        // 先獲取 訂單
        Order order = cacheOrderService.one(orderID);
        // 再獲取 配送
        LambdaQueryWrapper<Delivery> qw = new LambdaQueryWrapper<>();
        qw.eq(Delivery::getOrder_id, orderID);
        List<Delivery> deliveryList = this.list(qw);
        // 檢測
        if (deliveryList == null || deliveryList.isEmpty() || deliveryList.get(0) == null)
            return AjaxResultUtil.error("未找到該訂單的配送數據，請檢查訂單狀態");
        // 緩存
        Delivery delivery = deliveryList.get(0);
        cacheDeliveryService.saveToCache(delivery);
        // 返回
        return AjaxResultUtil.success(ViewDeliveryTransfer.transfer(delivery, order));
    }

    /**
    * 根據 ID
    */
    public AjaxResult one(Long deliveryID) {
        if (QTypeUtil.isNotLong(deliveryID)) return AjaxResultUtil.error("配送 ID 異常，請檢查 ID");
        return AjaxResultUtil.success(ViewDeliveryTransfer.transfer(cacheDeliveryService.one(deliveryID)));
    }
}
