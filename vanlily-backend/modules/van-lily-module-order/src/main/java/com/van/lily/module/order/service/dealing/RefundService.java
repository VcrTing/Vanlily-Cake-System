package com.van.lily.module.order.service.dealing;

import com.van.lily.commons.core.AjaxResult;
import com.van.lily.model.order.front.form.refund.FormRefund;

public interface RefundService {

    /**
    * 提交订单退单，普通退单
    */
    AjaxResult refundNormal(FormRefund form);

    /**
     * 强制订单退单
     */
    AjaxResult refundForce(FormRefund form);
}
