package com.van.lily.model.order.transfer;

import com.van.lily.commons.config.VanLilyConfigure;
import com.van.lily.commons.core.exception.QLogicException;
import com.van.lily.commons.define.enums.EnumBooleanTransfer;
import com.van.lily.commons.utils.qiong.QDateUtil;
import com.van.lily.model.order.entity.Delivery;
import com.van.lily.model.order.entity.Order;
import com.van.lily.model.order.transfer.order.OrderStatusTransfer;

import java.util.Date;

/**
* 这里是配送状态的对比
*/
public final class DeliveryStatusTransfer {

    /**
    * 正常工作
    */
    public static void isWorking(Delivery delivery) {
        if (delivery.getId() == null)
            throw new QLogicException("該配送 ID 缺失，請檢查該配送的數據信息");
        if (EnumBooleanTransfer.isTrue(delivery.getIs_over()))
            throw new QLogicException("該配送已經結束，已經無法再進行更改");
    }
    /**
    * 是否可以取貨
    */
    public static Boolean canConnect(Order order, Delivery delivery) {
        OrderStatusTransfer.isPaid(order);
        isWorking(delivery);
        if (order.getType_take() == null)
            throw new QLogicException("該訂單未加入配送數據，請檢查數據的完整性");
        if (EnumBooleanTransfer.isTrue(delivery.getIs_in_delivery()))
            throw new QLogicException("該訂單已經送出，正在配送中，已經無法再進行取貨");
        return true;
    }

    /**
    * 是否送出
    */
    public static Boolean isInDelivery(Order order, Delivery delivery) {
        OrderStatusTransfer.isPaid(order);
        isWorking(delivery);
        if (EnumBooleanTransfer.isFalse(delivery.getIs_in_delivery()))
            throw new QLogicException("該訂單不在配送中，請檢查訂單配送狀態");
        return true;
    }


    /**
    * 是否可以延迟送货
    */
    public static Boolean canDelay(Order order, Delivery delivery, Date delayEndTime) {
        OrderStatusTransfer.isPaid(order);
        isWorking(delivery);
        if (EnumBooleanTransfer.isTrue(delivery.getIs_in_delivery()))
            throw new QLogicException("該訂單在配送中，請檢查訂單配送狀態");
        // 是否在底線時間之後
        Date end = delivery.getReserve_delivery_time_end();
        Date limit = QDateUtil.afterHour(end, VanLilyConfigure.DELIVERY_DELAY_LIMIT_HOUR);
        // 超過底線時間
        if (QDateUtil.isAfter(delayEndTime, limit))
            throw new QLogicException("該訂單的延遲配送時間不合理，已經超過初次配送时间後" + VanLilyConfigure.DELIVERY_DELAY_LIMIT_HOUR + "小時");
        // 小於現在
        if (QDateUtil.isBefore(delayEndTime))
            throw new QLogicException("延遲配送時間不合理，時間值超前");
        return true;
    }
}
