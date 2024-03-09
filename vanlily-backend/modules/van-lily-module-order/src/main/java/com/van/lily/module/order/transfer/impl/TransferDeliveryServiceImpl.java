package com.van.lily.module.order.transfer.impl;

import cn.hutool.json.JSONUtil;
import com.van.lily.commons.core.exception.QLogicException;
import com.van.lily.commons.define.enums.EnumBooleanTransfer;
import com.van.lily.commons.utils.qiong.QBeanUtil;
import com.van.lily.commons.utils.qiong.QDateUtil;
import com.van.lily.commons.utils.qiong.QSumUtil;
import com.van.lily.model.order.define.enums.EnumOrderSendStatus;
import com.van.lily.model.order.define.enums.EnumOrderStatus;
import com.van.lily.model.order.define.enums.EnumOrderTakeType;
import com.van.lily.model.order.entity.Delivery;
import com.van.lily.model.order.entity.Order;
import com.van.lily.model.order.front.form.delivery.FormDelay;
import com.van.lily.model.order.front.form.delivery.FormDeliverySender;
import com.van.lily.model.order.front.form.delivery.jsonMan.FormDeliveryMan;
import com.van.lily.model.order.front.view.delivery.jsonDelay.ViewDeliveryDelay;
import com.van.lily.model.order.transfer.DeliveryTransfer;
import com.van.lily.module.order.transfer.TransferDeliveryService;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TransferDeliveryServiceImpl implements TransferDeliveryService {

    /**
    * 對接延遲配送
    */
    public Delivery asyncDelay(Delivery delivery, FormDelay form) {
        // 構建視圖
        ViewDeliveryDelay delay = QBeanUtil.convert(form, ViewDeliveryDelay.class);
        if (delay == null)
            throw new QLogicException("延遲收貨數據格式錯誤，延遲送貨失敗");
        form.setPublished(new Date());
        form.setIndex(form.getIndex());
        delivery.setDelay_remark(form.getRemark());
        delivery.setDelay(JSONUtil.toJsonStr(form));
        // 下次送達時間
        delivery.setReserve_arrive_time(DeliveryTransfer.reserveArriveTime(form.getReserve_delivery_time_start()));
        // 返回
        return delivery;
    }

    /**
    * 是否準時間
    */
    public Order quasiTime(Order order, Delivery delivery) {
        Date startTime = delivery.getReserve_delivery_time_start();
        Date endTime = delivery.getDelivery_end_time();

        // 是否準時
        if (QDateUtil.isAfter(startTime) && QDateUtil.isBefore(endTime)) {
            order.setStatus_send(EnumOrderSendStatus.delivered_normal);
        } else {
            // 在約定時間之前
            if (QDateUtil.isBefore(startTime)) {
                order.setStatus_send(EnumOrderSendStatus.delivered_ahead);
            }
            // 在約定時間之後
            if (QDateUtil.isAfter(endTime)) {
                order.setStatus_send(EnumOrderSendStatus.delivered_delay);
            }
        }
        return order;
    }

    /**
    * 完成訂單
    */
    public Order finishedOrder(Order order) {
        order.setStatus_life(EnumOrderStatus.finish);
        order.setIs_over(EnumBooleanTransfer.TRUE.v);
        order.setDate_over(new Date());
        return order;
    }

    /**
    * 強制自取
    */
    public Delivery forceSelfGet(Delivery delivery) {
        // 強制取貨類型為自體
        delivery.setType_take(EnumOrderTakeType.self_get);
        // 預計送達時間
        delivery.setNewest_arrive_time(new Date());
        delivery.setReserve_delivery_time_end(new Date());
        delivery.setReserve_delivery_time_start(new Date());
        // 強制結束配送
        delivery.setIs_in_delivery(EnumBooleanTransfer.FALSE.v);
        delivery.setIs_over(EnumBooleanTransfer.TRUE.v);
        // 返回
        return delivery;
    }

    /**
    * 对接送货员
    */
    public Delivery connectDeliveryMan(Delivery delivery, FormDeliveryMan man) {
        delivery.setDelivery_man(JSONUtil.toJsonStr(man));
        return delivery;
    }

    /**
    * 開始配送
    */
    public Delivery deliveryStart(Delivery delivery) {
        // 起送
        delivery.setDelivery_start_time(new Date());
        delivery.setIs_in_delivery(EnumBooleanTransfer.TRUE.v);
        return delivery;
    }

    /**
    * 完成送貨
    */
    public Delivery deliveryEnd(Delivery delivery) {
        // 送達
        delivery.setDelivery_end_time(new Date());
        delivery.setIs_over(EnumBooleanTransfer.TRUE.v);
        delivery.setIs_arrive(EnumBooleanTransfer.TRUE.v);
        delivery.setIs_in_delivery(EnumBooleanTransfer.FALSE.v);
        return delivery;
    }

}
