package com.van.lily.module.order.service.dealing;

import com.van.lily.commons.core.AjaxResult;

public interface PackageService {

    /**
    * 更改一个订单，可以打包
    */
    AjaxResult upd(Long orderID);
}
