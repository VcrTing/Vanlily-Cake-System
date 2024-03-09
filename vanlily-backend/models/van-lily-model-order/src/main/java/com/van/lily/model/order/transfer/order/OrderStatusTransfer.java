package com.van.lily.model.order.transfer.order;

import com.van.lily.commons.core.exception.QLogicException;
import com.van.lily.commons.define.enums.EnumBooleanTransfer;
import com.van.lily.commons.utils.qiong.QValueUtil;
import com.van.lily.model.order.define.enums.EnumOrderMakeStatus;
import com.van.lily.model.order.define.enums.EnumOrderPayStatus;
import com.van.lily.model.order.define.enums.EnumOrderSendStatus;
import com.van.lily.model.order.define.enums.EnumOrderStatus;
import com.van.lily.model.order.entity.Order;

/**
* 这里是订单状态的对比
*/
public final class OrderStatusTransfer {
    /**
    * 基本狀態查詢
    */
    public static void isWorking(Order order) {
        if (order.getId() == null)
            throw new QLogicException("該訂單 ID 缺失，請檢查該訂單的數據信息");
        if (EnumBooleanTransfer.isTrue(order.getIs_over()))
            throw new QLogicException("該訂單已經結束，已經無法再進行更改");
        if (!EnumOrderStatus.isInWorking(order.getStatus_life()))
            throw new QLogicException("該訂單狀態異常，不屬於可完善數據的狀態，請檢查訂單狀態信息");
    }
    public static void isPaid(Order order) {
        isWorking(order);
        if (EnumBooleanTransfer.isFalse(order.getIs_paid()))
            throw new QLogicException("該訂單未付款，請先讓付款訂單");
        if (QValueUtil.hasNoLength(order.getPayments()))
            throw new QLogicException("該訂單未找到付款信息，請先檢查訂單的付款信息");
    }

    /**
    * 重置订单的所有状态，為默認狀態
    */
    public static Order resetNull(Order order) {
        if (order.getStatus_make() == null) order.setStatus_make(EnumOrderMakeStatus.def());
        if (order.getStatus_send() == null) order.setStatus_send(EnumOrderSendStatus.def());
        if (order.getStatus_life() == null) order.setStatus_life(EnumOrderStatus.def());
        if (order.getIs_ahead() == null) order.setIs_ahead(EnumBooleanTransfer.TRUE.v);
        if (order.getIs_paid() == null) order.setIs_paid(EnumBooleanTransfer.FALSE.v);
        if (order.getIs_over() == null) order.setIs_over(EnumBooleanTransfer.FALSE.v);
        if (order.getIs_fill() == null) order.setIs_fill(EnumBooleanTransfer.FALSE.v);
        if (order.getIs_ahead() == null) order.setIs_ahead(EnumBooleanTransfer.FALSE.v);
        return order;
    }

    /**
    * 是否是可以加入数据的状态
    */
    public static Boolean canFilling(Order order) {
        isWorking(order);
        if (EnumBooleanTransfer.isTrue(order.getIs_paid()))
            throw new QLogicException("該訂單已付款，若完善信息會導致收費數據異常，所以無法執行完善數據操作");
        if (!EnumOrderMakeStatus.isInWaiting(order.getStatus_make()))
            throw new QLogicException("該訂單蛋糕已在製作中，已經無法再完善訂單數據了");
        if (EnumOrderSendStatus.isNotSilence(order.getStatus_send()))
            throw new QLogicException("該訂單已經聯絡配送員了，您無法在完善該訂單數據");
        return true;
    }

    /**
    * 是否能够 加入支付信息
    */
    public static Boolean canPayment(Order order) {
        isWorking(order);
        if (order.getPrice_generate() == null)
            throw new QLogicException("該訂單金額數據異常，請檢查待支付金額的數據信息");
        if (EnumBooleanTransfer.isFalse(order.getIs_fill()))
            throw new QLogicException("該訂單信息未被完善，請先檢查訂單信息的完善程度，收貨信息/蛋糕信息");
        if (EnumBooleanTransfer.isTrue(order.getIs_paid()))
            throw new QLogicException("該訂單已付款，無法執行完善付款數據操作");
        if (!EnumOrderMakeStatus.isInWaiting(order.getStatus_make()))
            throw new QLogicException("該訂單蛋糕已在製作中，已經無法再完善訂單數據了");
        return true;
    }

    /**
    * 是否能修改清單
    */
    public static Boolean canChecklist(Order order) {
        isWorking(order);
        if (EnumBooleanTransfer.isFalse(order.getIs_fill()))
            throw new QLogicException("該訂單信息未被完善，清單需要生成，親完善訂單信息");
        if (EnumBooleanTransfer.isFalse(order.getIs_paid()))
            throw new QLogicException("該訂單信息未付款，請付款該訂單後，才可檢查清單");
        if (!EnumOrderSendStatus.isInStore(order.getStatus_send()))
            throw new QLogicException("該訂單產品已經被送出，已經無法再進行清單更改");
        return true;
    }

    /**
    * 能更改打包完成
    */
    public static Boolean canPackage(Order order) {
        isWorking(order);
        if (EnumBooleanTransfer.isFalse(order.getIs_check_complete()))
            throw new QLogicException("該訂單清單信息未檢查完成，沒辦法進行訂單打包");
        if (order.getChecking_end_time() == null)
            throw new QLogicException("該訂單清單信息未檢查完成，沒辦法進行訂單打包");
        if (EnumBooleanTransfer.isFalse(order.getIs_paid()))
            throw new QLogicException("該訂單未付款，未付款的訂單，無法進行打包");
        if (!order.getStatus_make().equals(EnumOrderMakeStatus.make))
            throw new QLogicException("該訂單製作狀態不處於製作中，無法進行打包");
        return true;
    }

    /**
    * 能否退单
    */
    public static Boolean canRefundNormal(Order order) {
        isWorking(order);
        if (order.getStatus_make().equals(EnumOrderMakeStatus.packaged))
            throw new QLogicException("該訂單已經被打包，無法進行退單");
        if (EnumOrderSendStatus.isNotSilence(order.getStatus_send()))
            throw new QLogicException("該訂單配送已經開啟，無法進行退單");
        return true;
    }
}
