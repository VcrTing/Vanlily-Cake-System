package com.van.lily.module.order.controller;

import com.van.lily.commons.core.AjaxResult;
import com.van.lily.model.order.front.param.order.ParamGroupOrderSearch;
import com.van.lily.model.order.front.param.order.ParamOrderForSystem;
import com.van.lily.model.order.front.param.order.ParamOrderOperaBoard;
import com.van.lily.model.order.front.param.order.ParamOrderWorkBoard;
import com.van.lily.module.order.common.ApiRouter;
import com.van.lily.module.order.service.base.impl.OrderForSystemServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "订单列表")
@RestController
public class OrderForSystemController {

    @Autowired
    OrderForSystemServiceImpl service;

    /**
    * 深度列表，作用于系统查询
    */
    @GetMapping(ApiRouter.SYSTEM_ORDERS)
    public AjaxResult pag(ParamOrderForSystem param) {
        return service.pag(param);
    }

    /**
    * 活跃订单，操作板
    */
    @GetMapping(ApiRouter.SYSTEM_ORDERS_OPERA_BOARD)
    public AjaxResult operaBoard(ParamOrderOperaBoard param) {
        return service.operaBoard(param);
    }


    /**
    * 正式订单，工作板
    */
    @GetMapping(ApiRouter.SYSTEM_ORDERS_WORK_BOARD)
    public AjaxResult workBoard(ParamOrderWorkBoard param) {
        return service.workBoard(param);
    }

    /**
    * 深度搜索
    */
    @GetMapping(ApiRouter.SYSTEM_ORDERS_SEARCH)
    public AjaxResult search(ParamGroupOrderSearch param) {
        return service.search(param);
    }
}
