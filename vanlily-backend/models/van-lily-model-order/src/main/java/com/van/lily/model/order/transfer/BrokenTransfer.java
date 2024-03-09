package com.van.lily.model.order.transfer;

import com.van.lily.commons.config.VanLilyConfigure;
import com.van.lily.commons.core.exception.QLogicException;
import com.van.lily.commons.define.enums.EnumBooleanTransfer;
import com.van.lily.commons.utils.qiong.QBeanUtil;
import com.van.lily.commons.utils.qiong.QDateUtil;
import com.van.lily.commons.utils.qiong.QValueUtil;
import com.van.lily.model.order.define.enums.EnumOrderBrokenType;
import com.van.lily.model.order.define.enums.EnumPersonBelongType;
import com.van.lily.model.order.entity.BrokenOrder;
import com.van.lily.model.order.entity.Delivery;
import com.van.lily.model.order.entity.Order;
import com.van.lily.model.order.front.form.filling.jsonDelivery.FormOrderDelivery;
import com.van.lily.model.order.front.view.broken.jsonInner.ViewOrderBrokenParticipant;
import com.van.lily.model.order.front.view.delivery.jsonMan.ViewDeliveryMan;
import com.van.lily.model.system.entity.Customer;
import com.van.lily.model.system.entity.User;

import java.util.Date;

public final class BrokenTransfer {
    /**
    * 生成实体
    */
    public static BrokenOrder entityDefault(Order order, EnumOrderBrokenType typed) {
        BrokenOrder brokenOrder = new BrokenOrder();
        brokenOrder.setType_broken(typed);
        brokenOrder.setOrder_id(order.getId());
        brokenOrder.setOrder_uuid(order.getUuid());
        brokenOrder.setOrder_is_paid(order.getIs_paid());
        brokenOrder.setPrice_loss(order.getPrice_paid());
        brokenOrder.setPrice_order_origin(order.getPrice_generate());
        brokenOrder.setPublished(new Date());
        return brokenOrder;
    }

    /**
    * 員工數據轉 參與者
    */
    public static ViewOrderBrokenParticipant participantByStaff(User person) {
        ViewOrderBrokenParticipant participant = new ViewOrderBrokenParticipant();
        participant.setTyped(EnumPersonBelongType.staff);
        if (person == null) return participant;
        participant.setId(person.getId());
        participant.setName(person.getName());
        participant.setPhone(person.getPhone());
        return participant;
    }
    /**
    * 客人 參與者
    */
    public static ViewOrderBrokenParticipant participantByCustomer(Customer person) {
        ViewOrderBrokenParticipant participant = new ViewOrderBrokenParticipant();
        participant.setTyped(EnumPersonBelongType.customer);
        if (person == null) return participant;
        participant.setId(person.getId());
        participant.setName(person.getName());
        participant.setPhone(person.getPhone());
        return participant;
    }
    /**
    * 送貨員
    */
    public static ViewOrderBrokenParticipant participantByDeliveryMan(ViewDeliveryMan person) {
        ViewOrderBrokenParticipant participant = new ViewOrderBrokenParticipant();
        participant.setTyped(EnumPersonBelongType.customer);
        if (person == null) return participant;
        participant.setName(person.getName());
        participant.setPhone(person.getPhone());
        return participant;
    }
}
