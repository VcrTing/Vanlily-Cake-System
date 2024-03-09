package com.van.lily.model.order.transfer;

import cn.hutool.json.JSONUtil;
import com.van.lily.commons.config.VanLilyConfigure;
import com.van.lily.commons.core.exception.QLogicException;
import com.van.lily.commons.define.enums.EnumBooleanTransfer;
import com.van.lily.commons.utils.qiong.QBeanUtil;
import com.van.lily.commons.utils.qiong.QDateUtil;
import com.van.lily.commons.utils.qiong.QValueUtil;
import com.van.lily.model.order.define.enums.EnumOrderTakeType;
import com.van.lily.model.order.entity.Delivery;
import com.van.lily.model.order.entity.Order;
import com.van.lily.model.order.front.form.filling.jsonDelivery.FormOrderDelivery;
import com.van.lily.model.order.front.view.delivery.jsonMan.ViewDeliveryMan;

import java.util.Date;

public final class DeliveryTransfer {
    /**
    * 可用订单
    */
    public static Boolean isGood(Delivery src) { return src != null && src.getId() != null; }
    public static Boolean isBad(Delivery src) { return src == null || src.getId() == null; }

    /**
    * 獲取送貨員
    */
    public static ViewDeliveryMan getDeliveryMan(Delivery delivery) {
        String manString = delivery.getDelivery_man();
        return JSONUtil.toBean(QValueUtil.mustJsonBean(manString), ViewDeliveryMan.class);
    }

    /**
    * 配送时间是否是工作时间
    */
    public static Boolean isInWorkTime(Date start, Date end) {
        Integer hour = QDateUtil.getHour(start);
        if (hour < VanLilyConfigure.WORK_HOUR_START)
            throw new QLogicException("指定的時間區間不在工作時間內，請檢查開始時間");
        hour = QDateUtil.getHour(end);
        if (hour > VanLilyConfigure.WORK_HOUR_END)
            throw new QLogicException("指定的時間區間不在工作時間內，請檢查結束時間");
        return true;
    }

    /**
    * 生成默認的配送數據
    */
    public static Delivery entityDefault(Delivery src) {
        Delivery res = src;
        if (src == null) res = new Delivery();
        res.setIs_over(EnumBooleanTransfer.FALSE.v);
        res.setIs_delay(EnumBooleanTransfer.FALSE.v);
        res.setIs_accident(EnumBooleanTransfer.FALSE.v);
        res.setIs_in_delivery(EnumBooleanTransfer.FALSE.v);
        res.setPublished(new Date());
        return res;
    }

    /**
    * 計算預定送達時間
    */
    public static Date reserveArriveTime(Date startTime) {
        if (startTime == null)
            throw new QLogicException("預定起送時間為空，無法生成預計送達時間");
        return QDateUtil.afterHour(startTime, QDateUtil.HOUR_1);
    }

    /**
    * 生成配送數據，來自 訂單 完善信息
    */
    public static Delivery entityByFillingOrder(Order order, FormOrderDelivery src) {
        if (QValueUtil.hasNoLength(src.getReceive_name())) throw new QLogicException("完善訂單配送信息時，收貨人姓名為空");
        if (QValueUtil.hasNoLength(src.getReceive_phone())) throw new QLogicException("完善訂單配送信息時，收貨人電話號碼為空");

        // 轉換 BEAN
        Delivery res = QBeanUtil.convert(src, Delivery.class);
        if (res == null) throw new QLogicException("完善訂單配送信息時，配送信息的數據格式出現錯誤");
        res = entityDefault(res);
        if (order == null || order.getId() == null) throw new QLogicException("完善訂單配送信息時，該訂單數據未找到");

        // 關聯 訂單
        res.setOrder_id(order.getId());

        // 計算 預定送達時間
        Date reserve_arrive_time = reserveArriveTime(src.getReserve_delivery_time_start());
        // QDateUtil.afterHour(src.getReserve_delivery_time_start(), QDateUtil.HOUR_1);
        res.setReserve_arrive_time(reserve_arrive_time);
        res.setNewest_arrive_time(reserve_arrive_time);

        // 返回
        return res;
    }
}
