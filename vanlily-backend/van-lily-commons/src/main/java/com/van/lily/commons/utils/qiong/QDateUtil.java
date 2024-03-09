package com.van.lily.commons.utils.qiong;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public abstract class QDateUtil {
    public static Short MINUTE_1 = 1;
    public static Short MINUTE_60 = 60;
    public static Short HOUR_1 = 1;
    public static Short HOUR_24 = 24;
    public static String DEF_FMT = "yyyy-MM-dd";
    public static String DEF_FMT_LONG = DEF_FMT + " HH:mm:ss";
    public static String DEF_FMT_CN = "yyyy年MM月dd日";
    public static String DEF_FMT_LONG_CN = DEF_FMT_CN + " HH時mm分ss秒";

    /**
    * 获取几点
    */
    public static Integer getHour(Date src) {
        if (src == null) return 0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(src);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 转换 时间
     */
    public static Date convert(Object dateString, boolean isExact) {
        try {
            String src = dateString.toString().trim();
            return new SimpleDateFormat(isExact ? DEF_FMT_LONG : DEF_FMT).parse(src); }
        catch (Exception ignored) { } return null;
    }
    public static String convert(Date date, boolean isExactDate) {
        try {
            return new SimpleDateFormat(isExactDate ? DEF_FMT_LONG : DEF_FMT).format(date); }
        catch (Exception ignored) { } return null;
    }
    public static String convertCN(Date date, boolean isExactDate) {
        try {
            return new SimpleDateFormat(isExactDate ? DEF_FMT_LONG_CN : DEF_FMT_CN).format(date); }
        catch (Exception ignored) { } return null;
    }

    /**
     * 获取当前
     */
    public static String now(boolean isExact) {
        return convert(new Date(), isExact);
    }
    public static String now() {
        return convert(new Date(), false);
    }

    /**
    * 在现在之后 / 前
    */
    public static Boolean isAfter(Date src) { return src != null && src.after(new Date()); }
    public static Boolean isAfter(Date src, Date target) { return src != null && target != null && src.after(target); }
    public static Boolean isBefore(Date src) { return src != null && src.before(new Date()); }

    /**
    * 获取几小时之后，未來
    */
    public static Date afterHour(Date src, Number hour) {
        String h = hour == null ? HOUR_24.toString() : hour.toString();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(src);
        calendar.add(Calendar.HOUR_OF_DAY, Integer.parseInt(h));
        return calendar.getTime();
    }
    public static Date afterHour(Number hour) {
        return afterHour(new Date(), hour);
    }
    public static Date afterMinute(Date src, Number minute) {
        String m = minute == null ? MINUTE_1.toString() : minute.toString();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(src);
        calendar.add(Calendar.MINUTE, Integer.parseInt(m));
        return calendar.getTime();
    }

    /**
    * 過去
    */
    public static Date beforeHour(Date src, Number hour) {
        String h = hour == null ? HOUR_24.toString() : hour.toString();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(src);
        calendar.add(Calendar.HOUR_OF_DAY, -Integer.parseInt(h));
        return calendar.getTime();
    }
    public static Date beforeHour(Number hour) {
        return afterHour(new Date(), hour);
    }

    public static Date beforeMinute(Date src, Number minute) {
        String m = minute == null ? HOUR_1.toString() : minute.toString();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(src);
        calendar.add(Calendar.MINUTE, -Integer.parseInt(m));
        return calendar.getTime();
    }
}
