package com.van.lily.module.order.service.delivery.impl;

import com.van.lily.commons.core.AjaxResult;
import com.van.lily.commons.core.AjaxResultUtil;
import com.van.lily.commons.core.exception.QLogicException;
import com.van.lily.model.order.define.enums.EnumOrderTakeType;
import com.van.lily.model.order.entity.Delivery;
import com.van.lily.model.order.entity.Order;
import com.van.lily.model.order.front.form.delivery.FormArrive;
import com.van.lily.model.order.front.form.delivery.FormDeliverySender;
import com.van.lily.model.order.front.group.GroupOrderDelivery;
import com.van.lily.model.order.transfer.DeliveryStatusTransfer;
import com.van.lily.module.order.cache.impl.CacheDeliveryServiceImpl;
import com.van.lily.module.order.cache.impl.CacheOrderServiceImpl;
import com.van.lily.module.order.service.delivery.DeliveryOrderService;
import com.van.lily.module.order.transfer.impl.TransferDeliveryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeliveryOrderServiceImpl implements DeliveryOrderService {

    @Autowired
    CacheDeliveryServiceImpl cacheDeliveryService;
    @Autowired
    CacheOrderServiceImpl cacheOrderService;
    @Autowired
    TransferDeliveryServiceImpl transferDeliveryService;

    final Object selfGetLock = new Object();
    final Object deliveryLock = new Object();
    final Object deliveryGetLock = new Object();

    /**
    * 获取数据
    */
    public GroupOrderDelivery getData(Long deliveryID) {
        // 查询配送
        Delivery delivery = cacheDeliveryService.one(deliveryID);
        if (delivery.getOrder_id() == null) throw new QLogicException("該配送數據未連接到訂單數據，為無效配送數據");
        // 查詢所屬訂單
        Order order = cacheOrderService.one(delivery.getOrder_id());
        // 是否能连接
        Boolean canConnect = DeliveryStatusTransfer.canConnect(order, delivery);
        // 返回
        return new GroupOrderDelivery(order, delivery, canConnect);
    }

    /**
    * 强制自提
    */
    public AjaxResult selfGet(Long deliveryID) {
        synchronized (selfGetLock) {
            // 数据
            GroupOrderDelivery data = getData(deliveryID);
            Delivery delivery = data.getDelivery();
            Order order = data.getOrder();

            // 可以對接取貨
            if (data.getCanConnect()) {
                // 更改訂單狀態
                order = transferDeliveryService.quasiTime(order, delivery);

                // 原來是否是自取
                if (order.getType_take().equals(EnumOrderTakeType.self_get)) {
                    delivery = transferDeliveryService.forceSelfGet(delivery);
                }
                // 不是自取
                else {
                    delivery = transferDeliveryService.forceSelfGet(delivery);
                    // 強制自取提醒
                }

                // 完成訂單
                order = transferDeliveryService.finishedOrder(order);
                // 更改訂單
                cacheOrderService.upd(order);
                // 更改配送
                cacheDeliveryService.upd(delivery);
                // 完成配送的提醒

                // 返回
                return AjaxResultUtil.success(delivery);
            }
        }
        return AjaxResultUtil.error("配送狀態異常，無法進行配送對接");
    }

    /**
    * 配送
    */
    public AjaxResult delivery(Long deliveryID, FormDeliverySender form) {
        synchronized (deliveryLock) {
            // 先获取数据
            GroupOrderDelivery data = getData(deliveryID);
            Delivery delivery = data.getDelivery();
            Order order = data.getOrder();

            // 可以對接取貨
            if (data.getCanConnect()) {
                // 更改訂單狀態
                order = transferDeliveryService.quasiTime(order, delivery);
                 // 对接送货员
                delivery = transferDeliveryService.connectDeliveryMan(delivery, form.getDelivery_man());
                // 开始配送
                delivery = transferDeliveryService.deliveryStart(delivery);
                // 更改数据
                delivery.setType_take(EnumOrderTakeType.delivery_get);
                delivery.setNewest_arrive_time(form.getReserve_arrive_time());
                delivery.setReserve_arrive_time(form.getReserve_arrive_time());

                // 更改訂單
                cacheOrderService.upd(order);
                // 更改配送
                cacheDeliveryService.upd(delivery);
                // 开始配送的提醒

                // 返回
                return AjaxResultUtil.success(delivery);
            }
        }
        return AjaxResultUtil.error("配送狀態異常，無法進行配送對接");
    }

    /**
    * 配送自提
    */
    public AjaxResult deliveryGet(Long deliveryID, FormDeliverySender form) {
        synchronized (deliveryGetLock) {
            // 先获取数据
            GroupOrderDelivery data = getData(deliveryID);
            Delivery delivery = data.getDelivery();
            Order order = data.getOrder();

            // 可以對接取貨
            if (data.getCanConnect()) {
                // 更改訂單狀態
                order = transferDeliveryService.quasiTime(order, delivery);
                // 对接送货员
                delivery = transferDeliveryService.connectDeliveryMan(delivery, form.getDelivery_man());
                // 开始配送
                delivery = transferDeliveryService.deliveryStart(delivery);
                // 更改数据
                delivery.setType_take(EnumOrderTakeType.delivery_get);
                delivery.setNewest_arrive_time(form.getReserve_arrive_time());
                delivery.setReserve_arrive_time(form.getReserve_arrive_time());

                // 更改訂單
                cacheOrderService.upd(order);
                // 更改配送
                cacheDeliveryService.upd(delivery);
                // 开始配送的提醒

                // 返回
                return AjaxResultUtil.success(delivery);
            }
        }
        return AjaxResultUtil.error("配送狀態異常，無法進行配送對接");
    }

    /**
    * 送达
    */
    public AjaxResult arrive(Long deliveryID, FormArrive form) {

        // 查询配送
        Delivery delivery = cacheDeliveryService.one(deliveryID);
        if (delivery.getOrder_id() == null) throw new QLogicException("該配送數據未連接到訂單數據，為無效配送數據");
        // 查詢所屬訂單
        Order order = cacheOrderService.one(delivery.getOrder_id());
        // 是否能连接
        Boolean isInDelivery = DeliveryStatusTransfer.isInDelivery(order, delivery);
        // 处于送出中
        if (isInDelivery) {
            // 訂單送達結束
            delivery = transferDeliveryService.deliveryEnd(delivery);
            // 訂單結束
            order = transferDeliveryService.finishedOrder(order);
            // 备注
            delivery.setArrive_remark(form.getArrive_remark());

            // 更改訂單
            cacheOrderService.upd(order);
            // 更改配送
            cacheDeliveryService.upd(delivery);
            // 完成配送的提醒

            // 返回
            return AjaxResultUtil.success(delivery);
        }
        return null;
    }
}
