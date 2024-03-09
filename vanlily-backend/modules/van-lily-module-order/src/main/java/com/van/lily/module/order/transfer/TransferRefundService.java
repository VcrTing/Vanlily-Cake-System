package com.van.lily.module.order.transfer;

import com.van.lily.model.order.entity.Delivery;
import com.van.lily.model.order.entity.Order;
import com.van.lily.model.order.front.form.refund.FormRefund;

public interface TransferRefundService {

    /**
    * 對接退單信息
    */
    Order asyncRefund(Order order, FormRefund form);


}
