package com.van.lily.module.order.service.dealing.impl;

import cn.hutool.json.JSONUtil;
import com.van.lily.commons.core.AjaxResult;
import com.van.lily.commons.core.AjaxResultUtil;
import com.van.lily.commons.core.exception.QLogicException;
import com.van.lily.commons.utils.qiong.QTypeUtil;
import com.van.lily.model.order.define.enums.EnumOrderBrokenType;
import com.van.lily.model.order.entity.BrokenOrder;
import com.van.lily.model.order.entity.Delivery;
import com.van.lily.model.order.entity.Order;
import com.van.lily.model.order.front.form.refund.FormRefund;
import com.van.lily.model.order.front.view.broken.jsonInner.ViewOrderBrokenParticipant;
import com.van.lily.model.order.front.view.delivery.jsonMan.ViewDeliveryMan;
import com.van.lily.model.order.mapper.entity.BrokenOrderMapper;
import com.van.lily.model.order.transfer.BrokenTransfer;
import com.van.lily.model.order.transfer.DeliveryTransfer;
import com.van.lily.model.order.transfer.order.OrderStatusTransfer;
import com.van.lily.model.order.transfer.order.OrderTransfer;
import com.van.lily.model.system.entity.Customer;
import com.van.lily.model.system.entity.User;
import com.van.lily.module.order.bridge.impl.BridgeCustomerServiceImpl;
import com.van.lily.module.order.bridge.impl.BridgeUserServiceImpl;
import com.van.lily.module.order.cache.impl.CacheOrderServiceImpl;
import com.van.lily.module.order.service.dealing.BrokenGenerateService;
import com.van.lily.module.order.service.dealing.RefundService;
import com.van.lily.module.order.service.remind.impl.RemindStopServiceImpl;
import com.van.lily.module.order.transfer.impl.TransferRefundServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BrokenGenerateServiceImpl implements BrokenGenerateService {

    @Autowired
    CacheOrderServiceImpl cacheOrderService;
    @Autowired
    RemindStopServiceImpl remindStopService;
    @Autowired
    BrokenOrderMapper mapper;

    @Autowired
    BridgeCustomerServiceImpl bridgeCustomerService;
    @Autowired
    BridgeUserServiceImpl bridgeUserService;

    /**
    * 新增
    */
    public BrokenOrder pos(BrokenOrder entity) {
        int isOK = mapper.insert(entity);
        if (isOK <= 0) throw new QLogicException("因網絡波動，退單數據生成失敗");
        return entity;
    }

    /**
    * 生成
    */
    public BrokenOrder brokenByStaff(Order order, Long staffID, String remark) {
        if (OrderTransfer.isBadOrder(order)) throw new QLogicException("該訂單為無效訂單，生成壞單數據失敗");
        if (QTypeUtil.isNotLong(staffID)) throw new QLogicException("員工 ID 異常，生成壞單數據失敗");
        User user = bridgeUserService.one(staffID);
        if (user == null) throw new QLogicException("員工數據無法獲取，生成壞單數據失敗");
        BrokenOrder entity = BrokenTransfer.entityDefault(order, EnumOrderBrokenType.staff_opera_accident);
        entity.setRemark(remark);
        entity.setParticipant(JSONUtil.toJsonStr(BrokenTransfer.participantByStaff(user)));
        return pos(entity);
    }

    public BrokenOrder brokenByDelivery(Order order, Delivery delivery, String remark) {
        if (OrderTransfer.isBadOrder(order)) throw new QLogicException("該訂單為無效訂單，生成壞單數據失敗");
        ViewDeliveryMan man = DeliveryTransfer.getDeliveryMan(delivery);
        if (man == null) throw new QLogicException("送貨員數據無法獲取，生成壞單數據失敗");
        BrokenOrder entity = BrokenTransfer.entityDefault(order, EnumOrderBrokenType.delivery_accident);
        entity.setRemark(remark);
        entity.setParticipant(JSONUtil.toJsonStr(BrokenTransfer.participantByDeliveryMan(man)));
        return pos(entity);
    }

    public BrokenOrder brokenByRefund(Order order) {
        if (OrderTransfer.isBadOrder(order)) throw new QLogicException("該訂單為無效訂單，生成壞單數據失敗");
        Customer customer = bridgeCustomerService.one(order.getCustomer_id());
        if (customer == null) throw new QLogicException("客戶數據無法獲取，生成壞單數據失敗");
        BrokenOrder entity = BrokenTransfer.entityDefault(order, EnumOrderBrokenType.force_refund);
        entity.setRemark(customer.getName() + "客人執行強制退單");
        entity.setParticipant(JSONUtil.toJsonStr(BrokenTransfer.participantByCustomer(customer)));
        return pos(entity);
    }

    public BrokenOrder brokenBySanitation(Order order, ViewOrderBrokenParticipant participant, String remark) {
        if (OrderTransfer.isBadOrder(order)) throw new QLogicException("該訂單為無效訂單，生成壞單數據失敗");
        if (participant == null) throw new QLogicException("意外參與者數據無法獲取，生成壞單數據失敗");
        BrokenOrder entity = BrokenTransfer.entityDefault(order, EnumOrderBrokenType.delivery_accident);
        entity.setRemark(remark);
        entity.setParticipant(JSONUtil.toJsonStr(participant));
        return pos(entity);
    }
}
