package com.van.lily.access.define;

public interface CacheConstants {

    // 開頭
    String KEY_AUTH_PREFIX = "van:lily:auth:user:";

    // 令牌有效期（默认 7 天），redis 登录数据 存活时间
    int AUTH_EXPIRE_MINUET = 60 * 24 * 7;
}
