package com.van.lily.module.order.service.delivery;

import com.van.lily.commons.core.AjaxResult;
import com.van.lily.model.order.front.form.delivery.FormArrive;
import com.van.lily.model.order.front.form.delivery.FormDeliverySender;
import com.van.lily.model.order.front.param.delivery.ParamDelivery;

public interface DeliveryService {

    /**
    * 深度列表
    */
    AjaxResult pag(ParamDelivery param);

    /**
    * 根據 訂單 ID
    */
    AjaxResult oneByOrder(Long orderID);

    /**
    * 根據配送 ID
    */
    AjaxResult one(Long deliveryID);
}
