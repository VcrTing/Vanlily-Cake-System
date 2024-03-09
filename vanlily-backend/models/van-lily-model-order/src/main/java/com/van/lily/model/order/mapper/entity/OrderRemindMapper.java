package com.van.lily.model.order.mapper.entity;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.van.lily.model.order.entity.Order;
import com.van.lily.model.order.entity.remind.OrderRemind;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderRemindMapper extends BaseMapper<OrderRemind> {

}
