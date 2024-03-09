package com.van.lily.model.order.transfer.view;

import cn.hutool.json.JSONUtil;
import com.van.lily.commons.utils.qiong.QBeanUtil;
import com.van.lily.commons.utils.qiong.QValueUtil;
import com.van.lily.model.order.entity.Delivery;
import com.van.lily.model.order.entity.Order;
import com.van.lily.model.order.front.view.delivery.ViewDelivery;
import com.van.lily.model.order.front.view.delivery.jsonDelay.ViewDeliveryDelay;
import com.van.lily.model.order.front.view.delivery.jsonMan.ViewDeliveryMan;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public final class ViewDeliveryTransfer {

    public static ViewDelivery transfer(Delivery delivery, Order order) {
        ViewDelivery view = transfer(delivery);
        if (view == null) return null;
        view.setOrder(order);
        return view;
    }

    public static ViewDelivery transfer(Delivery delivery) {
        ViewDelivery view = QBeanUtil.convert(delivery, ViewDelivery.class);
        if (view == null) {
            log.error("轉換異常: DELIVERY TO VIEW ERROR，Method: ViewDeliveryTransfer.transfer");
            return null;
        }
        view.setDelay(JSONUtil.toBean(QValueUtil.mustJsonBean(delivery.getDelay()), ViewDeliveryDelay.class));
        view.setDelivery_man(JSONUtil.toBean(QValueUtil.mustJsonBean(delivery.getDelivery_man()), ViewDeliveryMan.class));

        return view;
    }

    public static List<ViewDelivery> transfers(List <Delivery> src) {
        return src.stream().map(ViewDeliveryTransfer::transfer).filter(Objects::nonNull).collect(Collectors.toList());
    }
}
