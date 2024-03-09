package com.van.lily.model.order.mapper.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.van.lily.model.order.dao.order.DaoOrder;
import com.van.lily.model.order.entity.Order;
import com.van.lily.model.order.front.view.order.ViewOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DaoOrderMapper extends BaseMapper<DaoOrder> {

    /**
    * 深度列表
    */
    <T> List<DaoOrder> multiDeep(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);

    /**
    * 深度详情
    */
    DaoOrder oneDeep(@Param("id") Long id);
}
