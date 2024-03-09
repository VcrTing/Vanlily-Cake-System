package com.van.lily.module.system.cache.impl;

import com.van.lily.commons.core.exception.QLogicException;
import com.van.lily.commons.keys.cache.KeySystemConstants;
import com.van.lily.framework.core.tools.RedisTool;
import com.van.lily.model.system.entity.User;
import com.van.lily.model.system.mapper.entity.UserMapper;
import com.van.lily.module.system.cache.CacheUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CacheUserServiceImpl implements CacheUserService {

    @Autowired
    RedisTool redisTool;

    @Autowired
    UserMapper mapper;

    /**
    * 新增
    */
    public User pos(User user) {
        int isOK = mapper.insert(user);
        if (isOK <= 0) throw new QLogicException("因網絡波動，用戶數據儲存失敗，請重識");
        saveToCache(user);
        return user;
    }

    // 存入緩存
    public void saveToCache(User src) {
        if (src != null && src.getId() != null) {
            String key = KeySystemConstants.USER_BY_ID + src.getId();
            redisTool.setObject(key, src);
        }
    }
}
