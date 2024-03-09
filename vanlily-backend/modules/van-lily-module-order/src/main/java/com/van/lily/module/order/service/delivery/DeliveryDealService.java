package com.van.lily.module.order.service.delivery;

import com.van.lily.commons.core.AjaxResult;
import com.van.lily.model.order.front.form.delivery.FormArrive;
import com.van.lily.model.order.front.form.delivery.FormDelay;
import com.van.lily.model.order.front.form.delivery.FormDeliverySender;

public interface DeliveryDealService {

    /**
    * 延迟送货
    */
    AjaxResult delay(Long deliveryID, FormDelay form);
}
