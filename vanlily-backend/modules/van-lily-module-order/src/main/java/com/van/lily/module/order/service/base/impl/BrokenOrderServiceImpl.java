package com.van.lily.module.order.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.van.lily.commons.core.AjaxResult;
import com.van.lily.commons.core.AjaxResultUtil;
import com.van.lily.commons.utils.PageHelperUtil;
import com.van.lily.model.order.entity.BrokenOrder;
import com.van.lily.model.order.front.param.broken.ParamBrokenOrder;
import com.van.lily.model.order.mapper.entity.BrokenOrderMapper;
import com.van.lily.model.order.transfer.view.ViewBrokenTransfer;
import com.van.lily.module.order.service.base.BrokenOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrokenOrderServiceImpl extends ServiceImpl<BrokenOrderMapper, BrokenOrder> implements BrokenOrderService {

    @Autowired
    BrokenOrderMapper mapper;

    public <T> AjaxResult pag(T param) {
        ParamBrokenOrder src = (ParamBrokenOrder) param;
        PageHelperUtil.start(src);
        return PageHelperUtil.successResult(ViewBrokenTransfer.transfers(this.list(src.queryWrapper())), src);
    }

    public AjaxResult one(Long id) {
        return AjaxResultUtil.success(ViewBrokenTransfer.transfer(mapper.selectById(id)));
    }

    public <T> AjaxResult pos(T form) {
        return null;
    }

    public <T> AjaxResult upd(Long id, T form) {
        return null;
    }

}
