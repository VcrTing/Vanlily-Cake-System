package com.van.lily.module.order.bridge.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.van.lily.commons.core.exception.QCloudException;
import com.van.lily.commons.keys.cache.KeySystemConstants;
import com.van.lily.framework.core.tools.RedisTool;
import com.van.lily.model.system.entity.Customer;
import com.van.lily.model.system.entity.User;
import com.van.lily.model.system.mapper.entity.UserMapper;
import com.van.lily.module.order.bridge.BridgeUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BridgeUserServiceImpl implements BridgeUserService {

    @Autowired
    UserMapper mapper;
    @Autowired
    RedisTool redisTool;

    public User one(Long id) {
        String key = KeySystemConstants.USER_BY_ID + id;
        User res = redisTool.getObject(key);
        if (res == null) {
            res = mapper.selectById(id);
            System.out.println(id);
            System.out.println(res);
            if (res != null) {
                saveToCache(res);
                redisTool.expire(key);
            }
        } else { redisTool.expire(key); }
        if (res == null) throw new QCloudException("員工數據為空，請檢查該客人是否存在。");
        return res;
    }
    public User one(String phone) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone, phone);
        return mapper.selectOne(queryWrapper);
    }

    public User oneByPhone(String phone) {
        String key = KeySystemConstants.USER_BY_PHONE + phone;
        User res = redisTool.getObject(key);
        if (res == null) {
            res = one(phone);
            if (res != null) {
                saveToCache(res);
                redisTool.expire(key);
            }
        } else { redisTool.expire(key); }
        if (res == null) throw new QCloudException("用戶數據為空，請檢查該用戶是否存在。");
        return res;
    }

    public void saveToCache(User user) {
        if (user != null && user.getId() != null) {
            // 根据 ID
            redisTool.setObject(KeySystemConstants.USER_BY_ID + user.getId(), user);
            // 根据 电话号码
            redisTool.setObject(KeySystemConstants.USER_BY_PHONE + user.getPhone(), user);
        }
    }
}
