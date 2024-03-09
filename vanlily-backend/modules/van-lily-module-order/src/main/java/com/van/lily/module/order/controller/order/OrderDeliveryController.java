package com.van.lily.module.order.controller.order;

import com.van.lily.commons.core.AjaxResult;
import com.van.lily.model.order.front.form.delivery.FormArrive;
import com.van.lily.model.order.front.form.delivery.FormDelay;
import com.van.lily.model.order.front.form.delivery.FormDeliverySender;
import com.van.lily.module.order.common.ApiRouter;
import com.van.lily.module.order.service.delivery.impl.DeliveryDealServiceImpl;
import com.van.lily.module.order.service.delivery.impl.DeliveryOrderServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "对接订单配送")
@RestController
public class OrderDeliveryController {

    @Autowired
    DeliveryOrderServiceImpl service;

    /**
    * 强制自提，
    * 以下全為 配送表 ID
    */
    @PostMapping(ApiRouter.DELIVERY_SELF_GET + ApiRouter.ID)
    public AjaxResult selfGet(@PathVariable Long id) {
        return service.selfGet(id);
    }

    /**
    * 對接平台配送員
    */
    @PostMapping(ApiRouter.DELIVERY_SENDER + ApiRouter.ID)
    public AjaxResult delivery(@PathVariable Long id, @RequestBody FormDeliverySender form) {
        return service.delivery(id, form);
    }

    /**
    * 配送自提
    */
    @PostMapping(ApiRouter.DELIVERY_SENDER_GET + ApiRouter.ID)
    public AjaxResult deliveryGet(@PathVariable Long id, @RequestBody FormDeliverySender form) {
        return service.deliveryGet(id, form);
    }

    @Autowired
    DeliveryDealServiceImpl dealService;

    /**
    * 提交送达
    */
    @PatchMapping(ApiRouter.ARRIVE + ApiRouter.ID)
    public AjaxResult arrive(@PathVariable Long id, @RequestBody FormArrive form) {
        return service.arrive(id, form);
    }

    /**
    * 延迟送货
    */
    @PatchMapping(ApiRouter.DELAY + ApiRouter.ID)
    public AjaxResult delay(@PathVariable Long id, @RequestBody FormDelay form) {
        return dealService.delay(id, form);
    }
}
