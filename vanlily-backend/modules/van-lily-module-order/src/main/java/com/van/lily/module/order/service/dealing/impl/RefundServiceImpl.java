package com.van.lily.module.order.service.dealing.impl;

import com.van.lily.commons.core.AjaxResult;
import com.van.lily.commons.core.AjaxResultUtil;
import com.van.lily.model.order.entity.Order;
import com.van.lily.model.order.front.form.refund.FormRefund;
import com.van.lily.model.order.transfer.order.OrderStatusTransfer;
import com.van.lily.module.order.cache.impl.CacheOrderServiceImpl;
import com.van.lily.module.order.service.dealing.BrokenGenerateService;
import com.van.lily.module.order.service.dealing.RefundService;
import com.van.lily.module.order.service.remind.impl.RemindStopServiceImpl;
import com.van.lily.module.order.transfer.impl.TransferRefundServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RefundServiceImpl implements RefundService {

    @Autowired
    CacheOrderServiceImpl cacheOrderService;
    @Autowired
    RemindStopServiceImpl remindStopService;
    @Autowired
    TransferRefundServiceImpl refundService;
    @Autowired
    BrokenGenerateServiceImpl brokenGenerateService;

    final Object refundLock = new Object();

    /**
    * 执行普通退货退款
    */
    public AjaxResult refundNormal(FormRefund form) {
        synchronized (refundLock) {
            // 获取订单
            Order order = cacheOrderService.one(form.getOrder_id());
            // 是否能退單
            if (OrderStatusTransfer.canRefundNormal(order)) {
                // 同步退單數據
                order = refundService.asyncRefund(order, form);
                // 更改訂單
                cacheOrderService.upd(order);
                // 取消提醒
                remindStopService.stopOrderRemind(order.getId());
                // 返回
                return AjaxResultUtil.success(order);
            }
        }
        return AjaxResultUtil.error("訂單狀態異常，無法進行訂單退單");
    }

    /**
    * 强制退单
    */
    public AjaxResult refundForce(FormRefund form) {
        synchronized (refundLock) {
            // 获取订单
            Order order = cacheOrderService.one(form.getOrder_id());
            // 同步退單數據
            order = refundService.asyncRefund(order, form);
            // 更改訂單
            cacheOrderService.upd(order);
            // 取消提醒
            remindStopService.stopOrderRemind(order.getId());
            // 生成壞單
            brokenGenerateService.brokenByRefund(order);
            // 返回
            return AjaxResultUtil.success(order);
        }
    }
}
