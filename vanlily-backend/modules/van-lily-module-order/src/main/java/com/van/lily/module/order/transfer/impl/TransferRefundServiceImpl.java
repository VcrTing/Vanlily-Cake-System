package com.van.lily.module.order.transfer.impl;

import cn.hutool.json.JSONUtil;
import com.van.lily.commons.core.exception.QLogicException;
import com.van.lily.commons.define.enums.EnumBooleanTransfer;
import com.van.lily.commons.utils.qiong.QBeanUtil;
import com.van.lily.model.order.define.enums.EnumOrderStatus;
import com.van.lily.model.order.entity.Order;
import com.van.lily.model.order.front.form.refund.FormRefund;
import com.van.lily.model.order.front.view.order.jsonRefund.ViewOrderRefund;
import com.van.lily.model.order.mapper.entity.OrderMapper;
import com.van.lily.module.order.transfer.TransferRefundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TransferRefundServiceImpl implements TransferRefundService {

    @Autowired
    OrderMapper mapper;

    /**
    * 同步退單
    */
    public Order asyncRefund(Order order, FormRefund form) {
        // 同步退單信息
        ViewOrderRefund refund = QBeanUtil.convert(form, ViewOrderRefund.class);
        if (refund == null)
            throw new QLogicException("退單表單數據格式異常，親檢查退單數據");
        refund.setPublished(new Date());
        order.setRefund(JSONUtil.toJsonStr(refund));
        order.setStatus_life(EnumOrderStatus.refunded);
        // 结束订单
        order.setIs_over(EnumBooleanTransfer.TRUE.v);
        order.setDate_over(new Date());
        // 返回
        return order;
    }
}
