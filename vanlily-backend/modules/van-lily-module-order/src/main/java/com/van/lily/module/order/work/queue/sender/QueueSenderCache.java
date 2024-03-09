package com.van.lily.module.order.work.queue.sender;

import cn.hutool.json.JSONUtil;
import com.van.lily.commons.keys.queue.KeyKafkaRemind;
import com.van.lily.framework.core.tools.KafkaTool;
import com.van.lily.model.order.entity.remind.OrderRemind;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueueSenderCache {

    @Autowired
    KafkaTool kafkaTool;

    /**
    * 更新缓存列表
    */
    public void refresh(OrderRemind ord) {
        kafkaTool.send(KeyKafkaRemind.TOPIC_REMIND_ORDER, JSONUtil.toJsonStr(ord));
        System.out.println("KAFKA 已发送");
    }
}
