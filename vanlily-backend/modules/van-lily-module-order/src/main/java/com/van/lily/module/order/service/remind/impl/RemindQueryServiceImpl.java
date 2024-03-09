package com.van.lily.module.order.service.remind.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.van.lily.commons.define.data.SqlFieldConstants;
import com.van.lily.commons.define.enums.EnumBooleanTransfer;
import com.van.lily.commons.utils.qiong.QDateUtil;
import com.van.lily.model.order.entity.remind.OrderRemind;
import com.van.lily.model.order.mapper.entity.OrderRemindMapper;
import com.van.lily.module.order.service.remind.RemindQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class RemindQueryServiceImpl implements RemindQueryService {

    final static Short DEF_BEFORE_MINUTE = 6;
    final static String FIELD_REMIND_TIME = "remind_time";
    final static String FIELD_IS_STOP = "is_stop";
    final static String FIELD_IS_OVER = "is_over";

    @Autowired
    OrderRemindMapper orderRemindMapper;

    /**
    * 生成 query wrapper，工作内的 提醒
    */
    public <T> QueryWrapper<T> workingWrapper() {
        QueryWrapper<T> qw = new QueryWrapper<>();
        qw.orderBy(true, false, SqlFieldConstants.ID);
        // 狀態
        qw.ne(FIELD_IS_STOP, EnumBooleanTransfer.TRUE.v);
        qw.ne(FIELD_IS_OVER, EnumBooleanTransfer.TRUE.v);
        // 返回
        return qw;
    }

    /**
    * 生成 query wrapper，查询 分钟之前的 活跃的 提醒
    */
    public <T> QueryWrapper<T> activeWrapper(Short beforeMinute) {
        QueryWrapper<T> qw = workingWrapper();
        // 時間
        qw.lt(FIELD_REMIND_TIME, new Date());
        qw.gt(FIELD_REMIND_TIME, QDateUtil.beforeMinute(new Date(), beforeMinute));
        // 返回
        return qw;
    }

    /**
    * 全部活跃的 提醒
    */
    public List<OrderRemind> activeOrderRemind() {
        return orderRemindMapper.selectList(activeWrapper(DEF_BEFORE_MINUTE));
    }

    /**
    * 该订单 活跃的 提醒
    */
    public List<OrderRemind> someOrderRemind(Long orderID) {
        QueryWrapper<OrderRemind> qw = workingWrapper();
        // 关于某订单 ID 的提醒
        qw.lambda().like(orderID != null, OrderRemind::getRemind_data, orderID.toString());
        return orderRemindMapper.selectList(qw);
    }

}
