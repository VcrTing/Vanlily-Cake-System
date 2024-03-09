package com.van.lily.module.order.service.dealing;

import com.van.lily.commons.core.AjaxResult;

public interface ChecklistService {

    /**
    * 查询该产品的 检查清单
    */
    AjaxResult list(Long orderID, Long productID);

    /**
    * 修改某项清单
    */
    // AjaxResult updItem(Long orderID, Long productID, ViewCheckItem item);
}
