package com.van.lily.module.order.service.dealing;

import com.van.lily.model.order.entity.BrokenOrder;
import com.van.lily.model.order.entity.Delivery;
import com.van.lily.model.order.entity.Order;
import com.van.lily.model.order.front.view.broken.jsonInner.ViewOrderBrokenParticipant;

public interface BrokenGenerateService {

    /**
    * 生成因为员工导致的坏货
    */
    BrokenOrder brokenByStaff(Order order, Long staffID, String remark);
    /**
    * 生成配送事故
    */
    BrokenOrder brokenByDelivery(Order order, Delivery delivery, String remark);
    /**
    * 生成强制退单
    */
    BrokenOrder brokenByRefund(Order order);
    /**
    * 生成卫生问题
    */
    BrokenOrder brokenBySanitation(Order order, ViewOrderBrokenParticipant participant, String remark);
}
