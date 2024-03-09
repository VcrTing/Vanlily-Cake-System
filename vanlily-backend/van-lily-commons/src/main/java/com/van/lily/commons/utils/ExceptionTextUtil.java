package com.van.lily.commons.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class ExceptionTextUtil {

    /**
    * Redis 錯誤信息
    */
    public final static Map<String, String> redisTexts = new HashMap<String, String>() {
        {
            put("RedisReadOnlyException", "緩存功能失效，連接失敗，需重識");
            put("Redis command timed out", "緩存功能失效，連接超時，重識多次失敗請聯絡工作人員");
            put("Unable to connect to Redis", "緩存功能失效，無法連接到，請通知工作人員開啟緩存");
        }
    };

    /**
    * 錯誤信息 轉換成中文
    */
    public static String convertText(String msgEn) {
        Optional<String> ops = redisTexts.keySet().stream().filter(msgEn::contains).findFirst();
        String k = ops.orElse(null);
        return k == null ? msgEn : redisTexts.get(k);
    }

}
