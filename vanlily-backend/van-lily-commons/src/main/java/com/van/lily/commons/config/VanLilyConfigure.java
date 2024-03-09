package com.van.lily.commons.config;

import java.math.BigDecimal;

public interface VanLilyConfigure {

    // 官方名稱
    String NAME = "元莉莉";
    String NAME_FULL = NAME + "蛋糕店";


    // 官方电邮发送者
    String[] OFFICIAL_EMAILS = {
            "vcrting@163.com",
            "vanlilycake@163.com",
            "vanlilycake@yaho.com",
            "vanlilycake@gmail.com"
    };

    // 配送延迟底线时间
    Short DELIVERY_DELAY_LIMIT_HOUR = 36;

    // 工作时间 (10:00 - 21:00)
    Short WORK_HOUR_START = 10;
    Short WORK_HOUR_END = 21;

    // 工作板最大訂單數
    Short LIMIT_DAY_OPERA_BOARD = 7;
    Short LIMIT_PAGE_OPERA_BOARD = 100;
    Short LIMIT_PAGE_WORK_BOARD = 50;

    // 最大金额误差限制
    BigDecimal MAX_PRICE_LIMIT = new BigDecimal("0.4");
}
