package com.van.lily.framework.core.tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class KafkaTool {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    Long i = 0L;
    final static Long MAX_I = 1000000L;

    /**
    * 规则 KEY 生成
    */
    public String key() {
        i = i + 1L;
        if (Objects.equals(i, MAX_I)) i = 0L; return i + "";
    }

    /**
    * 仅根据话题进行发送
    */
    public void send(String topic, String json) {
        kafkaTemplate.send(topic, key(), json);
    }

}
