package com.van.lily.commons.utils.define;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class QBetweenDate {
    // 可能為空
    private String starDate;
    private String endDate;

    private boolean isExact;

    public String getStarDate() { return this.starDate; }
    public String getEndDate() { return this.endDate; }

    public QBetweenDate() { }
    public QBetweenDate(String starDate, String endDate, boolean isExact) {
        this.starDate = starDate;
        this.endDate = endDate;
        this.isExact = isExact;
    }

    static String KEY_STAR_DATE = "starDate";
    static String KEY_END_DATE = "endDate";

    //
    static String DEF_FMT = "yyyy-MM-dd";
    static String DEF_FMT_LONG = DEF_FMT + " HH:mm:ss";

    /**
     * QUERY WRAPPER 條件
     * @params
     * @return
     */
    public boolean hasStar() { return (this.starDate != null); }
    public boolean hasEnd() { return (this.endDate != null); }
    public boolean has() { return (this.starDate != null) && (this.endDate != null); }

    public Date starDate(boolean isExact) { return stringToDate(this.starDate, isExact); }
    public Date endDate(boolean isExact) { return stringToDate(this.endDate, isExact); }

    /**
     * 當前時間 STRING
     * @params
     * @return
     */
    public static String nowDateString(boolean isExactDate) {
        return new SimpleDateFormat(isExactDate ? DEF_FMT_LONG : DEF_FMT).format(new Date());
    }

    /**
     * 是否是有效的 時間
     * @params
     * @return
     */
    public static String serDateString(Object dateString, boolean isExactDate) {
        if (dateString == null) return null;
        String src = dateString.toString().trim();
        if (src.isEmpty()) return null;
        return (stringToDate(src, isExactDate) != null) ? src : null;
    }
    public static String dateToString(Date date, boolean isExactDate) {
        return new SimpleDateFormat(isExactDate ? DEF_FMT_LONG : DEF_FMT).format(date);
    }
    public static Date stringToDate(String str, boolean isExactDate) {
        try { return new SimpleDateFormat(isExactDate ? DEF_FMT_LONG : DEF_FMT).parse(str.trim()); }
        catch (Exception ignored) { } return null;
    }

    /**
     *
     * @params MAP 是否是精確時間
     * @return
     */
    public static QBetweenDate init(Object star, Object end, boolean isExactDate) {
        return new QBetweenDate(serDateString(star, isExactDate), serDateString(end, isExactDate), isExactDate);
    }
    public static QBetweenDate init(Object end, boolean isExactDate) {
        return new QBetweenDate(serDateString(nowDateString(isExactDate), isExactDate), serDateString(end, isExactDate), isExactDate);
    }

    /**
     * 过去几天
     */
    public static QBetweenDate ofWhenDay(Number day, boolean isExact) {
        QBetweenDate res = new QBetweenDate();
        Date n = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, Integer.parseInt(day.toString()));

        if (day.intValue() >= 0) {
            // 开始
            res.starDate = (dateToString(n, isExact));
            // 结束
            res.endDate = (dateToString(calendar.getTime(), isExact));
        } else {
            // 开始
            res.endDate = (dateToString(n, isExact));
            // 结束
            res.starDate = (dateToString(calendar.getTime(), isExact));
        }

        res.isExact = isExact;

        // 返回
        return res;
    }
}
