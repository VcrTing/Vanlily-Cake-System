package com.van.lily.module.order.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.van.lily.commons.core.AjaxResult;
import com.van.lily.commons.core.AjaxResultUtil;
import com.van.lily.model.order.entity.Order;
import com.van.lily.model.order.mapper.entity.OrderMapper;
import com.van.lily.module.order.service.base.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    public <T> AjaxResult pos(T form) {

        return AjaxResultUtil.success(this.list());
    }

    public <T> AjaxResult upd(Long id, T form) {
        return null;
    }

    public <T> AjaxResult pag(T param) {
        return AjaxResultUtil.success(this.list());
    }

    public AjaxResult one(Long id) {
        return null;
    }
}
