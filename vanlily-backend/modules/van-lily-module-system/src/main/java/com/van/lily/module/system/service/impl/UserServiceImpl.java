package com.van.lily.module.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.van.lily.commons.core.AjaxResult;
import com.van.lily.commons.core.AjaxResultUtil;
import com.van.lily.model.system.entity.User;
import com.van.lily.model.system.front.form.user.FormUser;
import com.van.lily.model.system.mapper.entity.UserMapper;
import com.van.lily.model.system.transfer.UserTransfer;
import com.van.lily.module.system.cache.impl.CacheUserServiceImpl;
import com.van.lily.module.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    CacheUserServiceImpl cacheUserService;

    public <T> AjaxResult pos(T form) {
        FormUser formUser = (FormUser) form;
        // 電話號碼 查重複

        // 員工編號 查重複

        // 密碼加密

        // 返回
        return AjaxResultUtil.success(cacheUserService.pos(UserTransfer.entityByForm(formUser)));
    }

    public <T> AjaxResult upd(Long id, T form) {
        return null;
    }
}
