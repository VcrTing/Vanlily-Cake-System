package com.van.lily.module.order.service.remind.impl;

import com.van.lily.model.order.define.enums.EnumRemindOrderType;
import com.van.lily.model.order.entity.Order;
import com.van.lily.model.order.entity.remind.OrderRemind;
import com.van.lily.model.order.front.group.GroupOrderRemindForm;
import com.van.lily.model.order.mapper.entity.OrderRemindMapper;
import com.van.lily.model.order.transfer.remind.RemindGroupFormTransfer;
import com.van.lily.model.order.transfer.remind.RemindTransfer;
import com.van.lily.model.order.transfer.remind.RemindWorkTypeTransfer;
import com.van.lily.model.system.entity.Customer;
import com.van.lily.model.system.entity.User;
import com.van.lily.module.order.bridge.impl.BridgeCustomerServiceImpl;
import com.van.lily.module.order.bridge.impl.BridgeUserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class RemindGenerateServiceImpl {

    @Autowired
    OrderRemindMapper orderRemindMapper;
    @Autowired
    BridgeUserServiceImpl bridgeUserService;
    @Autowired
    BridgeCustomerServiceImpl bridgeCustomerService;

    /**
    * 生成中心，根據工作類型不同，生成不同的提醒
    */
    public boolean generate(Order order, EnumRemindOrderType workType) {
        // 提醒時間
        Date remindTime = RemindWorkTypeTransfer.switchRemindTime(order, workType);
        if (remindTime == null) {
            log.error("提醒生成失敗，REMIND TIME 找不到，工作類型: " + workType + "，地點: RemindGenerateServiceImpl.generate");
            return false;
        }
        if (!RemindTransfer.isGoodTime(remindTime)) {
            log.error("提醒生成失敗，REMIND TIME 时间不正常，时间在现在之前，工作類型: " + workType + "，地點: RemindGenerateServiceImpl.generate");
            return false;
        }
        // 寄出的 用戶 ID
        Long toID = RemindWorkTypeTransfer.switchToId(order, workType);
        if (toID == null) {
            log.error("提醒生成失敗，TO ID 找不到，工作類型: " + workType + "，地點: RemindGenerateServiceImpl.generate");
            return false;
        }
        // 生成表單
        GroupOrderRemindForm form = RemindGroupFormTransfer.generate(order, toID, remindTime, workType);
        if (form == null) {
            log.error("提醒生成失敗，GroupOrderRemindForm = null，工作類型: " + workType + "，地點: RemindGenerateServiceImpl.generate");
            return false;
        }
        // 生成订单提醒
        OrderRemind res = RemindTransfer.entityByForm(form);
        int isOK = orderRemindMapper.insert(res);
        if (isOK <= 0) log.error("提醒生成失敗，插入数据库失败，大概率網絡波動，工作類型: " + workType + "，地點: RemindGenerateServiceImpl.generate");
        // 返回
        return true;
    }
}
