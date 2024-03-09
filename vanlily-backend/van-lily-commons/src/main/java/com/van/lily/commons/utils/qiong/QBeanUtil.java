package com.van.lily.commons.utils.qiong;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Optional;

/**
* AUTHOR: VCR TING
*
*/
public abstract class QBeanUtil {

    /**
     * 執行 set 方法，只能傳 一個 值 的 例如 setName("AAA") 方法
     *
     */
    public static <T> void invokeBeanSet(T src, String methodName, Object v) {
        try {
            if ( v == null || src == null || methodName == null) { } else {
                Method method = src.getClass().getMethod(methodName, v.getClass());
                method.invoke(src, v);
            }
        } catch (Exception ignored) { }
    }

    /**
     * 獲取 set/get 方法
     *
     */
    public static Optional<String> groupSetMethod(Field f, boolean isSet) {
        return Optional.ofNullable(f)
                .map(Field::getName)
                .filter(s-> s.length() > 1)
                .map(s -> (isSet ? "set" : "get") + s.substring(0, 1).toUpperCase() + s.substring(1));
    }
    public static Optional<String> groupSetMethod(Field f) { return groupSetMethod(f, true); }

    /**
     * Bean 轉換
     *
     */
    public static <T, R> T convert(R src, Class<T> resClass) {
        if (src == null) return null;
        try {
            T res = resClass.newInstance();
            for (Field f: src.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                Object v = f.get(src);
                if (v != null) {
                    // 執行 值 搬運
                    invokeBeanSet(res, groupSetMethod(f).orElse(null), v);
                }
            }
            return res;
        } catch (Exception ignored) { } return null;
    }
    // 錯誤: java.lang.InstantiationException 是因为你没有空参数构造
}
