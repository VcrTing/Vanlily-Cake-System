package com.van.lily.module.order.service.base;

import com.van.lily.commons.core.AjaxResult;
import com.van.lily.model.order.front.param.order.ParamGroupOrderSearch;
import com.van.lily.model.order.front.param.order.ParamOrderForSystem;
import com.van.lily.model.order.front.param.order.ParamOrderWorkBoard;

public interface OrderForSystemService{

    /**
    * 深度分页查询列表
    */
    AjaxResult pag(ParamOrderForSystem param);

    /**
    * 工作薄
    */
    AjaxResult workBoard(ParamOrderWorkBoard param);

    /**
    * 根据ID、流水号、订单号、客人电话号码查询单个订单
    */
    AjaxResult search(ParamGroupOrderSearch param);
}
