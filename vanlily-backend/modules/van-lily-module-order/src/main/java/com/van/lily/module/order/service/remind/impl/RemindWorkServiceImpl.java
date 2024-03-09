package com.van.lily.module.order.service.remind.impl;

import cn.hutool.json.JSONUtil;
import com.van.lily.commons.define.enums.EnumBooleanTransfer;
import com.van.lily.framework.core.tools.SmsTool;
import com.van.lily.model.order.define.enums.EnumRemindOrderType;
import com.van.lily.model.order.define.enums.EnumRemindToType;
import com.van.lily.model.order.entity.Order;
import com.van.lily.model.order.entity.remind.OrderRemind;
import com.van.lily.model.order.mapper.entity.OrderRemindMapper;
import com.van.lily.model.order.transfer.remind.RemindMessageTransfer;
import com.van.lily.model.order.transfer.remind.RemindTransfer;
import com.van.lily.model.order.transfer.remind.RemindWorkTypeTransfer;
import com.van.lily.model.system.entity.Customer;
import com.van.lily.model.system.entity.User;
import com.van.lily.module.order.bridge.impl.BridgeCustomerServiceImpl;
import com.van.lily.module.order.bridge.impl.BridgeUserServiceImpl;
import com.van.lily.module.order.cache.impl.CacheOrderServiceImpl;
import com.van.lily.module.order.service.remind.RemindWorkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class RemindWorkServiceImpl implements RemindWorkService {

    @Autowired
    BridgeCustomerServiceImpl bridgeCustomerService;
    @Autowired
    BridgeUserServiceImpl bridgeUserService;
    @Autowired
    CacheOrderServiceImpl cacheOrderService;
    @Autowired
    OrderRemindMapper mapper;
    @Autowired
    SmsTool smsTool;

    /**
    * 獲取關聯數據
    */
    public Order getOrder(OrderRemind remind) {
        Long orderID = RemindTransfer.getOrderID(remind);
        if (orderID == null) return null;
        return cacheOrderService.one(orderID);
    }
    public Customer getCustomer(OrderRemind remind) {
        Long toID = RemindTransfer.getToID(remind, true);
        if (toID == null) return null;
        return bridgeCustomerService.one(toID);
    }
    public User getCashier(OrderRemind remind) {
        Long toID = RemindTransfer.getToID(remind, false);
        if (toID == null) return null;
        return bridgeUserService.one(toID);
    }

    /**
    * 發送短信 / ALL
    */
    boolean sendMsg(Order order, String to, OrderRemind remind) {
        if (to == null) log.error("提醒運作失敗，未找到接收方电话号码，地点：RemindWorkServiceImpl.sendMsg");
        if (order == null || to == null) return false;
        // 是否能发送
        if (!RemindWorkTypeTransfer.canSend(order, remind.getType_work())) {
            System.out.println("该条提醒不具备发送条件，remind = " + remind);
            return false;
        };
        // 正式发送
        if (remind.getType_to() == EnumRemindToType.message || remind.getType_to() == EnumRemindToType.all) {
            smsTool.sendMsg(to, RemindMessageTransfer.orderRemindMessage(order, remind));
            return true;
        }
        return false;
    }

    /**
    * 處理中心
    */
    public boolean dealingRemind(String ord) {
        OrderRemind data = JSONUtil.toBean(ord, OrderRemind.class);
        return data != null && dealingRemind(data);
    }
    public boolean dealingRemind(OrderRemind remind) {
        EnumRemindOrderType workType = remind.getType_work();
        boolean res = true;
        switch (workType) {
            case pay_not_yet_for_customer:
                res = doNotPayCustomer(remind);
                break;
            case pay_not_yet_for_cashier:
                res = doNotPayCashier(remind);
                break;
            case paid_for_customer:
                res = doPaidCustomer(remind);
                break;
            case paid_for_cashier:
                res = doPaidCashier(remind);
                break;
            case packaged_for_customer:
                res = doPackagedCustomer(remind);
                break;
            case packaged_for_cashier:
                res = doPackagedCashier(remind);
                break;
        }
        // 完成提醒
        finishedRemind(remind, res);
        // 提醒未发送的总览错误信息
        if (!res) log.debug("提醒運作失敗，請檢查詳細錯誤日誌，提醒類型 = " + workType + "，地點：RemindWorkServiceImpl.dealingRemind");
        return false;
    }

    /**
    * 完成提醒
    */
    void finishedRemind(OrderRemind remind, boolean isSuccess) {
        if (remind != null && remind.getId() != null) {
            remind.setIs_success(isSuccess ? EnumBooleanTransfer.TRUE.v : EnumBooleanTransfer.FALSE.v);
            remind.setDate_work(new Date());
            remind.setIs_over(EnumBooleanTransfer.TRUE.v);
            remind.setDate_over(new Date());
            mapper.updateById(remind);
        }
    }

    /**
    * 未支付
    */
    boolean doNotPayCustomer(OrderRemind remind) {
        // EnumRemindOrderType.pay_not_yet_for_customer
        boolean res = false;
        Customer human = getCustomer(remind);
        res = human != null && sendMsg(getOrder(remind), human.getEmail(), remind);
        return res;
    }
    boolean doNotPayCashier(OrderRemind remind) {
        // EnumRemindOrderType.pay_not_yet_for_cashier
        boolean res = false;
        User human = getCashier(remind);
        res = human != null && sendMsg(getOrder(remind), human.getEmail(), remind);
        return res;
    }

    /**
    * 已支付
    */
    boolean doPaidCustomer(OrderRemind remind) {
        // EnumRemindOrderType.pay_not_yet_for_customer
        boolean res = false;
        Customer human = getCustomer(remind);
        res = human != null && sendMsg(getOrder(remind), human.getEmail(), remind);
        return res;
    }
    boolean doPaidCashier(OrderRemind remind) {
        // EnumRemindOrderType.pay_not_yet_for_cashier
        boolean res = false;
        User human = getCashier(remind);
        res = human != null && sendMsg(getOrder(remind), human.getEmail(), remind);
        return res;
    }

    /**
    * 已打包
    */
    boolean doPackagedCustomer(OrderRemind remind) {
        // EnumRemindOrderType.pay_not_yet_for_customer
        boolean res = false;
        Customer human = getCustomer(remind);
        res = human != null && sendMsg(getOrder(remind), human.getEmail(), remind);
        return res;
    }
    boolean doPackagedCashier(OrderRemind remind) {
        // EnumRemindOrderType.pay_not_yet_for_cashier
        boolean res = false;
        User human = getCashier(remind);
        res = human != null && sendMsg(getOrder(remind), human.getEmail(), remind);
        return res;
    }
}
