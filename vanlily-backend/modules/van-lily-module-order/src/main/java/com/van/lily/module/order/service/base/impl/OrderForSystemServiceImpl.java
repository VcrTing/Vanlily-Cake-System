package com.van.lily.module.order.service.base.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.van.lily.commons.core.AjaxResult;
import com.van.lily.commons.utils.PageHelperUtil;
import com.van.lily.commons.utils.qiong.QValueUtil;
import com.van.lily.model.order.dao.order.DaoOrder;
import com.van.lily.model.order.front.param.order.ParamGroupOrderSearch;
import com.van.lily.model.order.front.param.order.ParamOrderForSystem;
import com.van.lily.model.order.front.param.order.ParamOrderOperaBoard;
import com.van.lily.model.order.front.param.order.ParamOrderWorkBoard;
import com.van.lily.model.order.mapper.dao.DaoOrderMapper;
import com.van.lily.model.order.mapper.entity.OrderMapper;
import com.van.lily.model.order.transfer.view.ViewOrderTransfer;
import com.van.lily.model.system.entity.Customer;
import com.van.lily.module.order.bridge.impl.BridgeCustomerServiceImpl;
import com.van.lily.module.order.service.base.OrderForSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderForSystemServiceImpl implements OrderForSystemService {

    @Autowired
    OrderMapper mapper;

    @Autowired
    DaoOrderMapper daoOrderMapper;

    @Autowired
    BridgeCustomerServiceImpl bridgeCustomerService;

    final static Object pagLock = new Object();
    final static Object manyLock = new Object();
    final static Object operaLock = new Object();
    final static Object workLock = new Object();

    /**
    * 深度訂單分頁列表
    */
    public AjaxResult pag(ParamOrderForSystem param) {
        synchronized (pagLock) {
            PageHelperUtil.start(param);
            return PageHelperUtil.successResult(ViewOrderTransfer.transfers(daoOrderMapper.multiDeep(param.queryWrapper())), param);
        }
    }

    /**
    * 订单操作板
    */
    public AjaxResult operaBoard(ParamOrderOperaBoard param) {
        synchronized (operaLock) {
            PageHelperUtil.start(param.resetPager());
            return PageHelperUtil.successResult(ViewOrderTransfer.transfers(daoOrderMapper.multiDeep(param.queryWrapper())), param);
        }
    }

    /**
    * 查詢工作板
    */
    public AjaxResult workBoard(ParamOrderWorkBoard param) {
        synchronized (workLock) {
            PageHelperUtil.start(param.resetPager());
            return PageHelperUtil.successResult(ViewOrderTransfer.transfers(daoOrderMapper.multiDeep(param.queryWrapper())), param);
        }
    }

    /**
    * 深度查詢
    */
    public AjaxResult search(ParamGroupOrderSearch param) {
        synchronized (manyLock) {
            PageHelperUtil.start(param.resetPager());
            QueryWrapper<DaoOrder> qw = param.queryWrapper();
            if (QValueUtil.hasLength(param.getPhone())) {
                // 獲取客人
                Customer customer = bridgeCustomerService.oneByPhone(param.getPhone());
                // 根據客人進行搜索
                qw.eq("me.customer_id", customer.getId());
            }
            return PageHelperUtil.successResult(ViewOrderTransfer.transfers(daoOrderMapper.multiDeep(qw)), param);
        }
    }
}
