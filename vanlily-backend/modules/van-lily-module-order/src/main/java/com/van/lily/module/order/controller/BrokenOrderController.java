package com.van.lily.module.order.controller;

import com.van.lily.commons.core.AjaxResult;
import com.van.lily.model.order.front.form.refund.FormRefund;
import com.van.lily.model.order.front.param.delivery.ParamDelivery;
import com.van.lily.module.order.common.ApiRouter;
import com.van.lily.module.order.service.base.impl.BrokenOrderServiceImpl;
import com.van.lily.module.order.service.dealing.impl.RefundServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Api(tags = "坏掉的订单")
@RequestMapping(ApiRouter.BROKEN_ORDER)
@RestController
public class BrokenOrderController {

    @Autowired
    BrokenOrderServiceImpl service;

    /**
    * 提交退貨退款
    */
    @GetMapping()
    public AjaxResult pag(ParamDelivery param) {
        return service.pag(param);
    }

    /**
    * 新增坏单数据
    */

}


