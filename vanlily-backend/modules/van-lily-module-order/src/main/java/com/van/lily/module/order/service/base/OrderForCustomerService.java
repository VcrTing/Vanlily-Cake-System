package com.van.lily.module.order.service.base;

import com.van.lily.commons.core.AjaxResult;
import com.van.lily.model.order.front.param.order.ParamGroupOrderSearch;
import com.van.lily.model.order.front.param.order.ParamOrderForSystem;
import com.van.lily.model.order.front.param.order.ParamOrderWorkBoard;

public interface OrderForCustomerService {

    /**
    * 深度分页查询列表，某位客人的
    */
    AjaxResult pag(ParamOrderForSystem param);

    /**
    * 根據狀態查詢，訂單量大是會分頁的
    */
    AjaxResult manyByStatus(ParamOrderWorkBoard param);

    /**
    * 根据ID、流水号、订单号、客人电话号码查询单个订单
    */
    AjaxResult manyByGroup(ParamGroupOrderSearch param);
}
