package com.van.lily.module.order.work.scheduling;

import com.van.lily.model.order.entity.remind.OrderRemind;
import com.van.lily.module.order.service.remind.impl.RemindQueryServiceImpl;
import com.van.lily.module.order.service.remind.impl.RemindWorkServiceImpl;
import com.van.lily.module.order.work.queue.sender.QueueSenderRemind;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RemindScheduling {

    @Autowired
    RemindQueryServiceImpl remindQueryService;

    @Autowired
    QueueSenderRemind senderRemind;

    /**
    * 一分钟执行一次
    */
    @Scheduled(fixedDelay = 1000 * 60)
    public void orderRemindScheduling() {
        // 查询所有 提醒
        List<OrderRemind> orderRemindList = remindQueryService.activeOrderRemind();
        // System.out.println("开始执行提醒：" + orderRemindList);
        // 處理提醒的發送
        // 交给 KAFKA 进行执行
        if (orderRemindList != null) orderRemindList.forEach(senderRemind::remindOrder);
    }
}
