package com.van.lily.model.system.mapper.entity;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.van.lily.model.system.entity.Customer;
import com.van.lily.model.system.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
