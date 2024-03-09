package com.van.lily.framework.core.tools;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@SuppressWarnings(value = {"unchecked", "rawtypes"})
@Slf4j
@Component
public class RedisTool {

    // 时间单位：分鐘
    final static TimeUnit TIME_UNIT = TimeUnit.MINUTES;
    // 未指定存活时间，默认存活：48 小时
    final static int DEF_LIVE_MINUTE = 24 * 60 * 2;

    @Autowired
    public RedisTemplate redisTemplate;

    /**
     * 随机存活时间
     */
    public int randomMinute(Integer src) {
        return src + (int) (Math.random() * 10);
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key      缓存的键值
     * @param value    缓存的值
     * @param timeout  时间
     */
    public <T> void setObject(final String key, final T value, final Integer timeout) {
        // log.debug("存入 REDIS，KEY = {}, V = {}", key, value);
        redisTemplate.opsForValue().set(key, value, timeout, TIME_UNIT);
    }
    public <T> void setObject(final String key, final T value) {
        setObject(key, value, DEF_LIVE_MINUTE);
    }


    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public <T> T getObject(final String key) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        // log.debug("取出 REDIS，KEY = {}, V = {}", key, operation.get(key));
        return operation.get(key);
    }

    /**
     * 删除单个对象
     *
     * @param key
     */
    public boolean delete(final String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }


    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout) {
        return Boolean.TRUE.equals(redisTemplate.expire(key, timeout, TIME_UNIT));
    }
    public boolean expire(final String key) {
        return Boolean.TRUE.equals(redisTemplate.expire(key, DEF_LIVE_MINUTE, TIME_UNIT));
    }

    /**
     * 缓存List数据，先刪除 key，再存入列表
     *
     * @param key      缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    public <T> void setCacheList(final String key, final List<T> dataList) {
        setCacheList(key, dataList, DEF_LIVE_MINUTE);
    }
    public <T> long setCacheList(final String key, final List<T> dataList, final long time) {
        delete(key);
        Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
        expire(key, time);
        return count == null ? 0 : count;
    }

    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    public <T> List<T> getCacheList(final String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }
}
