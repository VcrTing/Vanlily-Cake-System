package com.van.lily.module.order.service.dealing;

import com.van.lily.commons.core.AjaxResult;
import com.van.lily.model.order.front.form.filling.FormFillingOrder;
import com.van.lily.model.order.front.form.payment.FormPayOrder;
import com.van.lily.model.order.front.form.reserve.FormReserveOrder;

public interface CheckoutService {

    /**
    * 预约下单
    */
    AjaxResult reserveOrder(FormReserveOrder form);

    /**
    * 填充信息，正式确认订单
    */
    AjaxResult fillingOrder(FormFillingOrder form);

    /**
    * 提交支付信息
    */
    AjaxResult insertPayment(FormPayOrder form);

    /**
    * 关闭支付状态
    */

    /**
    * 打开支付状态
    */
}
