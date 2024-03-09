package com.van.lily.module.product.cache;


import com.van.lily.model.order.front.view.order.ViewOrder;
import com.van.lily.model.product.entity.Cake;
import com.van.lily.model.product.front.view.cake.ViewCake;


public interface CacheCakeService {

    /**
    * 获取 one cake
    */
    ViewCake daoOne(Long orderID);

    /**
    * 存入库存
    */
    void saveCake(Cake cake);
}
