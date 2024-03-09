package com.van.lily.module.order.transfer;

import com.van.lily.model.order.entity.Delivery;
import com.van.lily.model.order.entity.Order;
import com.van.lily.model.order.front.form.reserve.jsonOrder.FormReserveOrderProduct;
import com.van.lily.model.order.front.view.order.jsonOrder.ViewOrderProduct;

import java.util.List;

public interface TransferDeliveryService {

    /**
    * 是否準時取貨
    */
    Order quasiTime(Order order, Delivery delivery);

   /**
   * 完成訂單
   */
   Order finishedOrder(Order order);


    /**
    * 強制自取
    */
    Delivery forceSelfGet(Delivery delivery);

}
