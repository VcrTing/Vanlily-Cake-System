package com.van.lily.commons.utils.qiong;

import java.lang.reflect.Method;
import java.math.BigDecimal;

public abstract class QTypeUtil {

    /**
     * 字符串
     */
    public static boolean isStr(Object obj) { return (obj != null) && obj.getClass().equals(String.class); }
    public static String serStr(Object obj, Object def) { return (obj == null) ? def.toString() : obj.toString(); }
    public static String serStr(BigDecimal src) { return serStr(src, "0.00"); }

    /**
     * 数字类
     */
    public static <T> T serNumber(Object src, T def, Class<T> numberClass) {
        try {
            if (src == null) return def;
            Method method = numberClass.getDeclaredMethod("valueOf", String.class);
            Object res = method.invoke(null, src.toString());
            return (T) res;
        } catch (Exception ignored) {  } return def;
    }

    public static Long serLong(Object src, Long def) {
        return serNumber(src, def, Long.class);
    }
    public static Long serLong(Object src) {
        return serLong(src, null);
    }

    public static Integer serInt(Object src, Integer def) {
        return serNumber(src, def, Integer.class);
    }
    public static Integer serInt(Object src) { return serInt(src, null); }

    public static Short serShort(Object src, Short def) {
        return serNumber(src, def, Short.class);
    }
    public static Short serShort(Object src) {
        return serShort(src, null);
    }

    public static BigDecimal serBigDecimal(Object src, BigDecimal def) {
        if (src == null) return def;
        String str;
        if (src instanceof String) {
            str = src.toString().trim();
            return str.isEmpty() ? def : new BigDecimal(str);
        }
        str = src.toString().trim();
        return str.isEmpty() ? def : new BigDecimal(str);
    }

    /**
    * 是否是一个 LONG
    */
    public static Boolean isLongs(Object ...src) {
        boolean result = true;
        try {
            for (Object s: src) {
                if (s == null) return false;
                Long.parseLong(s.toString());
            }
        } catch (Exception ignored) { result = false; }
        return result;
    }
    public static Boolean isLong(Object src) {
        if (src == null) return false;
        if (src instanceof Long) return true;
        try {
            Long.parseLong(src.toString());
            return true;
        } catch (Exception ignored) { } return false;
    }
    public static Boolean isNotLong(Object src) { return !isLong(src); }
}