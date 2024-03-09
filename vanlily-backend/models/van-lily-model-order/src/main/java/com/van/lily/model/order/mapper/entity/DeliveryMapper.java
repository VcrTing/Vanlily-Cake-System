package com.van.lily.model.order.mapper.entity;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.van.lily.model.order.entity.Delivery;
import com.van.lily.model.order.entity.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeliveryMapper extends BaseMapper<Delivery> {

}
