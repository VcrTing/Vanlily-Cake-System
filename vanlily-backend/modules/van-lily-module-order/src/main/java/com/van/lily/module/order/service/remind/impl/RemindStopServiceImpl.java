package com.van.lily.module.order.service.remind.impl;

import com.van.lily.commons.define.enums.EnumBooleanTransfer;
import com.van.lily.commons.utils.qiong.QTypeUtil;
import com.van.lily.commons.utils.qiong.QValueUtil;
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
import com.van.lily.module.order.service.remind.RemindStopService;
import com.van.lily.module.order.service.remind.RemindWorkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class RemindStopServiceImpl implements RemindStopService {

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

    @Autowired
    RemindQueryServiceImpl queryService;

    void stopRemind(OrderRemind remind) {
        remind.setIs_stop(EnumBooleanTransfer.TRUE.v);
        remind.setDate_stop(new Date());
        int isOK = mapper.updateById(remind);
        if (isOK <= 0) log.error("暂停提醒失败，因网络波动修改暂停数据失败，地点：RemindStopServiceImpl.stopRemind");
    }

    /**
    * 暂停订单提醒
    */
    public void stopOrderRemind(Long orderID) {
        if (QTypeUtil.isLong(orderID)) {
            // 取出订单
            Order order = cacheOrderService.one(orderID);
            // 取出所有相关提醒
            List<OrderRemind> remindList = queryService.someOrderRemind(orderID);

            for (OrderRemind remind: remindList) {
                // 再次对比 ID
                Long id = RemindTransfer.getOrderID(remind);
                if (id.equals(orderID)) {

                    boolean stop = false;
                    // 若订单 不处于工作状态
                    if (!RemindWorkTypeTransfer.isWorking(order)) {
                        stop = true;
                    } else {
                        // 若不能进行发送
                        if (!RemindWorkTypeTransfer.canSend(order, remind.getType_work())) {
                            stop = true;
                        }
                    }
                    // if (stop) System.out.println("暂停一个提醒，该提醒是：" + remind);
                    // 暂停该提醒
                    if (stop) stopRemind(remind);
                }
            }
        }
    }

}
