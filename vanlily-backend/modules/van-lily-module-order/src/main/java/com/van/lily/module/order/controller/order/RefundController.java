package com.van.lily.module.order.controller.order;

import com.van.lily.commons.core.AjaxResult;
import com.van.lily.commons.core.AjaxResultUtil;
import com.van.lily.model.order.front.form.refund.FormRefund;
import com.van.lily.module.order.common.ApiRouter;
import com.van.lily.module.order.service.base.impl.OrderServiceImpl;
import com.van.lily.module.order.service.dealing.impl.RefundServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Api(tags = "订单退货")
@RestController
public class RefundController {

    @Autowired
    RefundServiceImpl service;

    /**
    * 提交退貨退款
    */
    @PostMapping(ApiRouter.REFUND_NORMAL)
    public AjaxResult refundNormal(@RequestBody FormRefund form) {
        return service.refundNormal(form);
    }

    /**
    * 强制退单
    */
    @PostMapping(ApiRouter.REFUND_FORCE)
    public AjaxResult refundForce(@RequestBody FormRefund form) {
        return service.refundForce(form);
    }
}


