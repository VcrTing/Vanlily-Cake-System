package com.van.lily.module.product.service.tool;

import com.van.lily.model.product.entity.Cake;

public interface CakeToolService {
    /**
    * 根据产品编号查
    */
    Cake oneByNumber(String number);
}
