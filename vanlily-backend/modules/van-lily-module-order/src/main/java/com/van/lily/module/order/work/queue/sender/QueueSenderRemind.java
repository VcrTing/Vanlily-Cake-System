package com.van.lily.module.order.work.queue.sender;

import cn.hutool.json.JSONUtil;
import com.van.lily.commons.keys.queue.KeyKafkaOrder;
import com.van.lily.commons.keys.queue.KeyKafkaRemind;
import com.van.lily.framework.core.tools.KafkaTool;
import com.van.lily.model.order.define.enums.EnumRemindOrderType;
import com.van.lily.model.order.entity.Order;
import com.van.lily.model.order.entity.remind.OrderRemind;
import com.van.lily.model.order.front.group.GroupRemindOrderGenerateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class QueueSenderRemind {

    @Autowired
    KafkaTool kafkaTool;

    /**
    * 生成 提醒 订单
    */
    public void remindOrderGenerates(Order order, EnumRemindOrderType ...workTypes) {
        Arrays.stream(workTypes).forEach(wt -> remindOrderGenerate(order, wt));
    }
    public void remindOrderGenerate(Order order, EnumRemindOrderType workType) {
        GroupRemindOrderGenerateForm rogForm = new GroupRemindOrderGenerateForm(order, workType);
        kafkaTool.send(KeyKafkaRemind.TOPIC_REMIND_ORDER_GENERATE, JSONUtil.toJsonStr(rogForm));
    }

    /**
    * 发送提醒处理：订单
    */
    public void remindOrder(OrderRemind ord) {
        kafkaTool.send(KeyKafkaRemind.TOPIC_REMIND_ORDER, JSONUtil.toJsonStr(ord));
        System.out.println("KAFKA 已发送");
    }
}
