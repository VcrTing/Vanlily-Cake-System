package com.van.lily.module.order.work.queue;

import cn.hutool.json.JSONUtil;
import com.van.lily.commons.keys.queue.KeyKafkaRemind;
import com.van.lily.model.order.entity.remind.OrderRemind;
import com.van.lily.model.order.front.group.GroupRemindOrderGenerateForm;
import com.van.lily.module.order.service.remind.impl.RemindGenerateServiceImpl;
import com.van.lily.module.order.service.remind.impl.RemindWorkServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class QueueRemindDealing {

    @Autowired
    RemindWorkServiceImpl remindWorkService;

    @Autowired
    RemindGenerateServiceImpl remindGenerateService;

    /**
    * 生成 提醒
    */
    @KafkaListener(topics = KeyKafkaRemind.TOPIC_REMIND_ORDER_GENERATE)
    public void doGenerate(String json) {
        GroupRemindOrderGenerateForm rogForm = JSONUtil.toBean(json, GroupRemindOrderGenerateForm.class);
        if (rogForm != null) remindGenerateService.generate(rogForm.getOrder(), rogForm.getWorkType());
        else log.error("【提醒生成失败】 GroupRemindOrderGenerateForm 数据为空，地点：QueueRemindDealing doGenerate");
    }

    /**
    * 接收提醒 订单
    */
    @KafkaListener(topics = KeyKafkaRemind.TOPIC_REMIND_ORDER)
    public void doRemindOrder(String json) {
        remindWorkService.dealingRemind(json);
    }
}
