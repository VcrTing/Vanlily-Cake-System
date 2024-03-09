package com.van.lily.module.order.service.delivery;

import com.van.lily.commons.core.AjaxResult;
import com.van.lily.model.order.front.form.delivery.FormArrive;
import com.van.lily.model.order.front.form.delivery.FormDeliverySender;

public interface DeliveryOrderService {

    /**
    * 送达
    */
    AjaxResult arrive(Long deliveryID, FormArrive form);

    /**
    * 强制自提
    */
    AjaxResult selfGet(Long deliveryID);

    /**
    * 配送
    */
    AjaxResult delivery(Long delivery, FormDeliverySender form);

    /**
    * 配送自提
    */
    AjaxResult deliveryGet(Long deliveryID, FormDeliverySender form);

}
