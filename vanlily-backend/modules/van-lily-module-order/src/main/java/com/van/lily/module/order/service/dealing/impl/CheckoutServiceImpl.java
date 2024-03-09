package com.van.lily.module.order.service.dealing.impl;

import cn.hutool.json.JSONUtil;
import com.van.lily.commons.core.AjaxResult;
import com.van.lily.commons.core.AjaxResultUtil;
import com.van.lily.commons.core.exception.QLogicException;
import com.van.lily.commons.define.enums.EnumBooleanTransfer;
import com.van.lily.commons.utils.qiong.QDateUtil;
import com.van.lily.commons.utils.qiong.QTypeUtil;
import com.van.lily.commons.utils.qiong.QValueUtil;
import com.van.lily.model.order.define.enums.EnumOrderMakeStatus;
import com.van.lily.model.order.define.enums.EnumRemindOrderType;
import com.van.lily.model.order.entity.Delivery;
import com.van.lily.model.order.entity.Order;
import com.van.lily.model.order.front.form.filling.FormFillingOrder;
import com.van.lily.model.order.front.form.filling.jsonProduct.FormFillingOrderProduct;
import com.van.lily.model.order.front.form.payment.FormPayOrder;
import com.van.lily.model.order.front.form.reserve.jsonOrder.FormCustomerSimply;
import com.van.lily.model.order.front.form.reserve.FormReserveOrder;
import com.van.lily.model.order.front.form.reserve.jsonOrder.FormReserveOrderProduct;
import com.van.lily.model.order.front.view.order.jsonOrder.ViewOrderProduct;
import com.van.lily.model.order.mapper.entity.DeliveryMapper;
import com.van.lily.model.order.mapper.entity.OrderMapper;
import com.van.lily.model.order.transfer.DeliveryTransfer;
import com.van.lily.model.order.transfer.order.OrderStatusTransfer;
import com.van.lily.model.order.transfer.order.OrderTransfer;
import com.van.lily.model.system.entity.Customer;
import com.van.lily.module.order.bridge.impl.BridgeCakeServiceImpl;
import com.van.lily.module.order.bridge.impl.BridgeCustomerServiceImpl;
import com.van.lily.module.order.cache.CacheOrderService;
import com.van.lily.module.order.service.dealing.CheckoutService;
import com.van.lily.module.order.service.remind.impl.RemindGenerateServiceImpl;
import com.van.lily.module.order.service.remind.impl.RemindStopServiceImpl;
import com.van.lily.module.order.transfer.impl.TransferChecklistServiceImpl;
import com.van.lily.module.order.transfer.impl.TransferCheckoutServiceImpl;
import com.van.lily.module.order.work.queue.sender.QueueSenderRemind;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    @Autowired
    TransferCheckoutServiceImpl transferCheckoutService;
    @Autowired
    TransferChecklistServiceImpl transferChecklistService;
    @Autowired
    RemindGenerateServiceImpl remindGenerateService;

    @Autowired
    QueueSenderRemind queueSenderRemind;

    @Autowired
    RemindStopServiceImpl remindStopService;
    @Autowired
    BridgeCustomerServiceImpl bridgeCustomerService;
    @Autowired
    CacheOrderService cacheOrderService;
    @Autowired
    BridgeCakeServiceImpl cakeService;

    @Autowired
    OrderMapper mapper;
    @Autowired
    DeliveryMapper deliveryMapper;

    final Object reserveLock = new Object();
    final Object fillingLock = new Object();
    final Object paymentLock = new Object();

    /**
    * 首次下单，预约下单
    */
    public AjaxResult reserveOrder(FormReserveOrder form) {
        // 记录收银员
        if (QTypeUtil.isNotLong(form.getCashier_id())) return AjaxResultUtil.error("收銀員信息未紀錄，下單失敗，請先檢查表單完整性");
        // 查找客人信息，未找到，就創建一個客人
        FormCustomerSimply customerSimply = form.getCustomer();
        Customer customer = bridgeCustomerService.oneOrCreat(customerSimply.getPhone(), customerSimply.getName());
        if (customer != null && QValueUtil.hasLength(customer.getPhone())) {

            // 生成订单产品，根据产品列表生成
            List<FormReserveOrderProduct> orderProductList = form.getProducts();
            // 組裝訂單產品，FormReserveOrderProduct TO ViewOrderProduct
            List<ViewOrderProduct> viewCakeList = transferCheckoutService.reserveProductsToDataProducts(orderProductList);
            // 生成预约订单，此时订单是未付款状态
            Order order = OrderTransfer.entityFromReserveParam(form);
            order.setCustomer_id(customer.getId());
            order.setIs_ahead(form.getIs_ahead());
            // 將新產品數據組裝進去
            order.setProducts(OrderTransfer.jsonOrderProduct(viewCakeList));
            // 返回
            return AjaxResultUtil.success(cacheOrderService.pos(order));
        }
        return AjaxResultUtil.error("客人信息錯誤，下單失敗，請先檢查客人信息完整性");
    }

    /**
    * 完善订单信息
    */
    public AjaxResult fillingOrder(FormFillingOrder form) {
        synchronized (fillingLock) {
            // 先獲取訂單
            Order order = cacheOrderService.one(form.getId());
            if (order == null) return AjaxResultUtil.error("無法找到訂單信息，請先確保該訂單真實存在");
            // 只有能夠進行完善數據，才放行
            if (OrderStatusTransfer.canFilling(order)) {
                // 組裝基本數據
                order = transferCheckoutService.fillingBasicData(order, form.getCharge(), form.getDiscounts(), form.getRemarks());
                // 訂單產品
                List<FormFillingOrderProduct> productList = form.getProducts();
                // 再次組裝訂單產品，FormFillingOrderProduct TO ViewOrderProduct
                List<ViewOrderProduct> viewCakeList = transferCheckoutService.fillingProductsToDataProducts(productList);
                order.setProducts(JSONUtil.toJsonStr(viewCakeList));
                order.setDiscounts(JSONUtil.toJsonStr(form.getDiscounts()));

                // 組裝 配送數據
                Delivery delivery = DeliveryTransfer.entityByFillingOrder(order, form.getDelivery());
                // 先插入 配送數據
                deliveryMapper.insert(delivery);
                // 配送數據完善之後
                if (delivery.getId() != null) {

                    // 訂單金額 與 儲存折扣
                    order = transferCheckoutService.asyncOrderPrice(order, viewCakeList, form.getDiscounts());
                    // 更新訂單取貨類型
                    order.setType_take(delivery.getType_take());
                    // 訂單信息已完善
                    order.setIs_fill(EnumBooleanTransfer.TRUE.v);
                    order.setDate_filling(new Date());
                    // 預送貨時間後 5 分鐘，即是店鋪準備送貨時間
                    order.setDate_reserve_delivery(QDateUtil.afterMinute(delivery.getReserve_delivery_time_start(), 5));
                    // 修改
                    order = cacheOrderService.upd(order);
                    // 生成提醒 BY KAFKA
                    queueSenderRemind.remindOrderGenerates(order, EnumRemindOrderType.pay_not_yet_for_cashier, EnumRemindOrderType.pay_not_yet_for_customer);
                    // 更改訂單數據
                    return AjaxResultUtil.success(order);
                }
                throw new QLogicException("因網絡波動，無法生成配送數據，本次訂單信息完善失敗");
            }
        }
        return AjaxResultUtil.error("訂單狀態異常，無法進行信息完善");
    }

    /**
    * 加入支付信息
    */
    public AjaxResult insertPayment(FormPayOrder form) {
        synchronized (paymentLock) {
            // 先獲取訂單
            Order order = cacheOrderService.one(form.getId());
            if (order == null || order.getId() == null) return AjaxResultUtil.error("無法找到訂單信息，請先確保該訂單真實存在");

            // 先验核状态
            if (OrderStatusTransfer.canPayment(order)) {
                // 同步支付信息
                order = transferCheckoutService.asyncPayment(order, form.getPayments());
                // 生成流水号
                order.setArticle_number(transferCheckoutService.generateArticleNumber());
                // 紀錄負責人
                order.setCharge_checkout(JSONUtil.toJsonStr(form.getCharge_checkout()));
                // 生成检查清单
                order.setChecking_list(JSONUtil.toJsonStr(transferChecklistService.generateCheckList(order)));

                // 更改为订单制作中的状态，具体真正的什么时候开始制作，无需说明
                order.setStatus_make(EnumOrderMakeStatus.make);
                // 更新订单
                order = cacheOrderService.upd(order);
                // 生成提醒 BY KAFKA
                queueSenderRemind.remindOrderGenerates(order, EnumRemindOrderType.paid_for_cashier, EnumRemindOrderType.paid_for_customer);
                // 暂停一些提醒
                remindStopService.stopOrderRemind(order.getId());
                // 返回
                return AjaxResultUtil.success(order);
            }
        }
        return AjaxResultUtil.error("訂單狀態異常，無法進行支付信息的完善");
    }
}
