package com.van.lily.module.order.controller;

import com.van.lily.commons.core.AjaxResult;
import com.van.lily.model.order.front.form.delivery.FormArrive;
import com.van.lily.model.order.front.form.delivery.FormDelay;
import com.van.lily.model.order.front.form.delivery.FormDeliverySender;
import com.van.lily.model.order.front.param.delivery.ParamDelivery;
import com.van.lily.module.order.common.ApiRouter;
import com.van.lily.module.order.service.delivery.impl.DeliveryDealServiceImpl;
import com.van.lily.module.order.service.delivery.impl.DeliveryOrderServiceImpl;
import com.van.lily.module.order.service.delivery.impl.DeliveryServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "配送列表")
@RestController
public class DeliveryController {

    @Autowired
    DeliveryServiceImpl service;

    /**
    * 深度 分頁列表
    */
    @GetMapping(ApiRouter.DELIVERIES)
    public AjaxResult pag(ParamDelivery param) {
        return service.pag(param);
    }

    /**
    * 根據 配送 ID
    */
    @GetMapping(ApiRouter.DELIVERIES + ApiRouter.ID)
    public AjaxResult one(@PathVariable Long id) {
        return service.one(id);
    }

    /**
    * 根據 訂單 ID
    */
    @GetMapping(ApiRouter.DELIVERIES_BY_ORDER + ApiRouter.ID)
    public AjaxResult byOrder(@PathVariable Long id) { return service.oneByOrder(id); }
}
