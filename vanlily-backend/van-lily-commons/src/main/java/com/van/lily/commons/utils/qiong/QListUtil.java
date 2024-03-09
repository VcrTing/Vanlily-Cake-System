package com.van.lily.commons.utils.qiong;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public abstract class QListUtil {
    /**
    * 淘洗
    */
    public static <T> List<T> must(List<T> src) { return src == null ? new ArrayList<>() : src; }

    /**
    * 有效性
    */
    public static <T> Boolean isGoodList(List<T> src) {
        return src != null && !src.isEmpty();
    }
    public static <T> Boolean isBadList(List<T> src) {
        return src == null || src.isEmpty();
    }
}
