package com.van.lily.module.order.service.remind;

public interface RemindStopService {
    /**
    * 根据订单 ID 停止该订单相关的 提醒
    */
    void stopOrderRemind(Long orderID);
}
