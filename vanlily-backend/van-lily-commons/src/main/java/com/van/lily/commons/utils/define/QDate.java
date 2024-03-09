package com.van.lily.commons.utils.define;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class QDate {
    // 可能為空
    private Date date;
    private boolean isExact;

    //
    static String DEF_FMT = "yyyy-MM-dd";
    static String DEF_FMT_LONG = DEF_FMT + " HH:mm:ss";

    public boolean has() { return date != null; }
    /**
     * 是否是有效的 時間
     * @params
     * @return
     */
    public static Date serDate(Object dateString, boolean isExactDate) {
        if (dateString == null) return null; String src = dateString.toString().trim();
        if (src.isEmpty()) return null;
        try { return new SimpleDateFormat(isExactDate ? DEF_FMT_LONG : DEF_FMT).parse(src); }
        catch (ParseException ignored) { } return null;
    }
}
