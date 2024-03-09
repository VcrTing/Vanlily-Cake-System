package com.van.lily.commons.utils;

public abstract class FormTextUtil {

    final static String EMPTY = "";
    final static String AT = "@";
    final static String POINT = ".";

    static String ser(Object src) {
        if (src == null) return EMPTY;
        return src.toString().trim();
    }

    /**
    * 是否是電郵地址
    */
    public static boolean isEmail(Object src) {
        String res = ser(src);
        if (res.isEmpty()) return false;
        return res.contains(AT) && res.contains(POINT);
    }
}
