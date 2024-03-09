package com.van.lily.module.order.work.queue;

import com.van.lily.commons.keys.queue.KeyKafkaRemind;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class QueueDelivery {


    /**
    * 生成
    */
    @KafkaListener(topics = KeyKafkaRemind.TOPIC_REMIND_ORDER)
    public void doDeliveryInsert() {

    }
}
