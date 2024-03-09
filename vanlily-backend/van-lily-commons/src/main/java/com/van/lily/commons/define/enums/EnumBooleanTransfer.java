package com.van.lily.commons.define.enums;

public enum EnumBooleanTransfer {

    TRUE((short) 1),
    FALSE((short) 0);

    public static short getTrue() {
        return TRUE.v;
    }
    public static short getFalse() {
        return FALSE.v;
    }

    /**
    * 是否都可用
    */
    public static Boolean isNice(Short ...src) {
        for (Short s: src)
            if (s == null || s  > 2 || s < 0) return false;
        return true;
    }
    public static Boolean isNiceOr(Short ...src) {
        for (Short s: src)
            if (s != null && s >= 0 && s < 2) return true;
        return false;
    }
    /**
    * 是否正確值
    */
    public static Boolean isTrue(Short src) { return src != null && src.equals(EnumBooleanTransfer.TRUE.v); }
    public static Boolean isTrues(Short ...src) {
        for (Short s: src)
            if (s == null || s.equals(EnumBooleanTransfer.FALSE.v))
                return false;
        return true;
    }
    public static Boolean isFalse(Short src) { return src != null && src.equals(EnumBooleanTransfer.FALSE.v); }

    public final short v;
    EnumBooleanTransfer(short v) { this.v = v; }

    public static short transfer(Boolean src) {
        if (src == null) return FALSE.v;
        if (src.equals(true)) return TRUE.v;
        return FALSE.v;
    }

    public static boolean transfer(Short src) {
        if (src == null) return false;
        return src.equals(TRUE.v);
    }
}
