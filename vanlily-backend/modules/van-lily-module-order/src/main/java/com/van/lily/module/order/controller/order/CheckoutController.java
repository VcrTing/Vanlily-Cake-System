package com.van.lily.module.order.controller.order;

import com.van.lily.commons.core.AjaxResult;
import com.van.lily.model.order.front.form.filling.FormFillingOrder;
import com.van.lily.model.order.front.form.payment.FormPayOrder;
import com.van.lily.model.order.front.form.reserve.FormReserveOrder;
import com.van.lily.module.order.common.ApiRouter;
import com.van.lily.module.order.service.dealing.impl.CheckoutServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "订单下单")
@RestController
public class CheckoutController {

    @Autowired
    CheckoutServiceImpl checkoutService;

    /**
    * 下单步骤第一：初次下单，预约订单，
    * 客人信息，简单的日期信息，主要是先生成一个订单再說，後續數據會補上
    */
    @PostMapping(ApiRouter.RESERVE)
    public AjaxResult reserve(@RequestBody FormReserveOrder form) {
        return checkoutService.reserveOrder(form);
    }

    /**
    * 下单步骤第二：完善订单信息
    * 输入一些信息，完善订单，比如说：蛋糕选取确认、取货和收货设置、蛋糕字粒确认、制作工期确认
    */
    @PostMapping(ApiRouter.FILLING)
    public AjaxResult filling(@RequestBody FormFillingOrder form) {
        return checkoutService.fillingOrder(form);
    }

    /**
    * 下单步骤第三：录入付款
    * 订单信息已经确认，该收多少钱也已经确认，接下来就是等待客户付款，未付款有未付款的处理
    */
    @PostMapping(ApiRouter.PAYMENT)
    public AjaxResult payment(@RequestBody FormPayOrder form) {
        return checkoutService.insertPayment(form);
    }

}
