package com.van.lily.commons.utils.qiong;

import io.swagger.models.auth.In;

import java.math.BigDecimal;

public abstract class QValueUtil {
    final static String TEXT_JSON_BEAN = "{}";
    final static String TEXT_JSON_LIST = "[]";
    /**
    * 随机整数
    */
    public static Integer randomInt() { return (int) (Math.random() * 100 + 1); }
    public static Integer randomInt(Integer limit) { return (int) (Math.random() * limit + 1); }

    /**
    * 字符串量化
    */
    public static String serString(Object src) {
        if (src == null) return null;
        return src.toString().trim();
    }
    public static String mustJsonBean(Object src) {
        if (src == null) return TEXT_JSON_BEAN;
        String res = src.toString().trim();
        if (res.isEmpty()) return TEXT_JSON_BEAN;
        return (res.equals(TEXT_JSON_LIST)) ? TEXT_JSON_BEAN : src.toString();
    }
    public static String mustJsonList(Object src) {
        if (src == null) return TEXT_JSON_LIST;
        String res = src.toString().trim();
        if (res.isEmpty()) return TEXT_JSON_LIST;
        return (res.equals(TEXT_JSON_BEAN)) ? TEXT_JSON_LIST : src.toString();
    }

    /**
    * 字符串比较
    */
    public static Boolean hasText(String src, String text) {
        if (src == null || text == null) return false;
        return src.toUpperCase().contains(text.toUpperCase());
    }

    /**
    * 是否有一个数值
    */
    public static Boolean hasString(String src) { return !src.trim().isEmpty(); }
    public static Boolean hasLength(Object src) {
        return src != null && hasString(src.toString());
    }
    public static Boolean hasNoLength(Object src) {
        return src == null || !hasString(src.toString());
    }

    /**
    * 多重 不为空
    */
    public static Boolean has(Object ...src) {
        for (Object o: src) { if (o == null) return false; }
        return true;
    }

    /**
    * 有效 BigDecimal
    */
    public static Boolean hasDecimal(BigDecimal src) {
        if (src == null) return false;
        return src.compareTo(BigDecimal.ZERO) > 0;
    }
}
