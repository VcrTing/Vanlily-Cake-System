package com.van.lily.module.order.transfer;

import com.van.lily.model.order.front.form.reserve.jsonOrder.FormReserveOrderProduct;
import com.van.lily.model.order.front.view.order.jsonOrder.ViewOrderProduct;

import java.util.List;

public interface TransferCheckoutService {

    /**
    * 根据预约下单产品 转为 数据库储存格式产品
    */
    List<ViewOrderProduct> reserveProductsToDataProducts(List<FormReserveOrderProduct> orderProductList);
}
