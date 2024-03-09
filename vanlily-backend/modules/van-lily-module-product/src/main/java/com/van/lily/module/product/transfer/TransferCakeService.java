package com.van.lily.module.product.transfer;

import com.van.lily.model.product.entity.Cake;
import com.van.lily.model.product.front.form.cake.FormCake;

public interface TransferCakeService {

    /**
    * FORM TO ENTITY
    */
    Cake formToEntity(FormCake form);
}
