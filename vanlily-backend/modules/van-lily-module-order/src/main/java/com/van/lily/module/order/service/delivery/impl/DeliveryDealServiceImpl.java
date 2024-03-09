package com.van.lily.module.order.service.delivery.impl;

import com.van.lily.commons.core.AjaxResult;
import com.van.lily.commons.core.AjaxResultUtil;
import com.van.lily.commons.core.exception.QLogicException;
import com.van.lily.model.order.define.enums.EnumOrderSendStatus;
import com.van.lily.model.order.define.enums.EnumOrderStatus;
import com.van.lily.model.order.entity.Delivery;
import com.van.lily.model.order.entity.Order;
import com.van.lily.model.order.front.form.delivery.FormArrive;
import com.van.lily.model.order.front.form.delivery.FormDelay;
import com.van.lily.model.order.transfer.DeliveryStatusTransfer;
import com.van.lily.model.order.transfer.DeliveryTransfer;
import com.van.lily.module.order.cache.impl.CacheDeliveryServiceImpl;
import com.van.lily.module.order.cache.impl.CacheOrderServiceImpl;
import com.van.lily.module.order.service.delivery.DeliveryDealService;
import com.van.lily.module.order.transfer.impl.TransferDeliveryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DeliveryDealServiceImpl implements DeliveryDealService {

    @Autowired
    CacheDeliveryServiceImpl cacheDeliveryService;
    @Autowired
    CacheOrderServiceImpl cacheOrderService;

    @Autowired
    TransferDeliveryServiceImpl transferDeliveryService;

    /**
    * 延迟送货
    */
    public AjaxResult delay(Long deliveryID, FormDelay form) {
        // 查询配送
        Delivery delivery = cacheDeliveryService.one(deliveryID);
        if (delivery.getOrder_id() == null) throw new QLogicException("該配送數據未連接到訂單數據，為無效配送數據");
        // 查詢所屬訂單
        Order order = cacheOrderService.one(delivery.getOrder_id());
        // 新的配送時間段
        Date end = form.getReserve_delivery_time_end();
        Date star = form.getReserve_delivery_time_start();
        // 是否是正常工作時間 10:00 - 21:00
        if (DeliveryTransfer.isInWorkTime(star, end)) {

            // 是否可以延迟送货
            if (DeliveryStatusTransfer.canDelay(order, delivery, end)) {
                // 正式延遲入貨
                delivery = transferDeliveryService.asyncDelay(delivery, form);
                // 訂單狀態
                order.setStatus_send(EnumOrderSendStatus.delayed);
                // 更改訂單
                cacheOrderService.upd(order);
                // 更改配送
                cacheDeliveryService.upd(delivery);
                // 延遲配送的提醒

                return AjaxResultUtil.success(delivery);
            }
        }
        return AjaxResultUtil.error("訂單狀態異常，無法修改延遲送貨信息");
    }
}
