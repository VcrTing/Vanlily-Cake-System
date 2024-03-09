package com.van.lily.model.order.transfer.remind;

import com.sun.org.apache.xpath.internal.operations.Or;
import com.van.lily.commons.define.enums.EnumBooleanTransfer;
import com.van.lily.commons.utils.qiong.QDateUtil;
import com.van.lily.model.order.define.enums.EnumOrderStatus;
import com.van.lily.model.order.define.enums.EnumOrderTakeType;
import com.van.lily.model.order.define.enums.EnumRemindOrderType;
import com.van.lily.model.order.entity.Order;

import java.util.Date;

public final class RemindWorkTypeTransfer {

    static Date afterTime(Date src, int num) {
        return (src != null) ? QDateUtil.afterMinute(src, num) : null;
    }
    static Date beforeTime(Date src, int num) {
        return (src != null) ? QDateUtil.beforeMinute(src, num) : null;
    }

    /**
    * 订单必须处于工作状态
    */
    public static boolean isWorking(Order order) {
        if (order == null || order.getId() == null) return false;
        if (
                order.getStatus_life() == EnumOrderStatus.refunded ||
                order.getStatus_life() == EnumOrderStatus.canceled
        ) return false;
        return true;
    }

    /**
    * 何时拒绝发送
    */
    public static boolean canSend(Order order, EnumRemindOrderType workType) {
        // 订单必须处于工作状态
        if (!isWorking(order)) return false;
        switch (workType) {
            case pay_not_yet_for_customer:
                // 支付了就不用这个提醒
                return EnumBooleanTransfer.isFalse(order.getIs_paid());
            case pay_not_yet_for_cashier:
                // 支付了就不用这个提醒
                return EnumBooleanTransfer.isFalse(order.getIs_paid());
            case paid_for_customer:
            case paid_for_cashier:
                // 工作状态即可
                return true;
            case packaged_for_customer:
                // 自提与配送自提需要生成
                return !EnumOrderTakeType.onlySend(order.getType_take());
            case packaged_for_cashier:
                // 自提与配送自提需要生成
                return !EnumOrderTakeType.onlySend(order.getType_take());
        }
        return false;
    }

    /**
    * 提醒功能不同，提醒時間也不同
    */
    public static Date switchRemindTime(Order order, EnumRemindOrderType workType) {
        // 獲取數據
        switch (workType) {
            case pay_not_yet_for_customer:
                // 完善數據 60 分鐘之後
                return afterTime(order.getDate_filling(), 60);
            case pay_not_yet_for_cashier:
                // 完善數據 15 分鐘之後
                return afterTime(order.getDate_filling(), 15);
            case paid_for_customer:
                // 付款 1 分鐘之後
                return afterTime(order.getDate_paid(), 1);
            case paid_for_cashier:
                // 預定配送時間 30 分鐘之前
                return beforeTime(order.getDate_reserve_delivery(), 30);
            case packaged_for_customer:
                // 打包完成 15 分鐘之後
                return afterTime(order.getDate_packaged(), 15);
            case packaged_for_cashier:
                // 預定配送時間 1 分鐘之前
                return beforeTime(order.getDate_reserve_delivery(), 1);
        }
        return null;
    }

    /**
    * 根據類型不同，獲取不同的 接收者 ID
    */
    public static Long switchToId(Order order, EnumRemindOrderType workType) {
        Long toID = null;
        // 獲取數據
        switch (workType) {
            case pay_not_yet_for_customer:
                toID = order.getCustomer_id();
                break;
            case pay_not_yet_for_cashier:
                toID = order.getCashier_id();
                break;
            case paid_for_customer:
                toID = order.getCustomer_id();
                break;
            case paid_for_cashier:
                toID = order.getCashier_id();
                break;
            case packaged_for_customer:
                toID = order.getCustomer_id();
                break;
            case packaged_for_cashier:
                toID = order.getCashier_id();
                break;
        }
        return toID;
    }

}
