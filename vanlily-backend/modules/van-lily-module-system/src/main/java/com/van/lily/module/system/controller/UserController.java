package com.van.lily.module.system.controller;

import com.van.lily.commons.core.AjaxResult;
import com.van.lily.model.system.front.form.user.FormUser;
import com.van.lily.module.system.common.ApiRouter;
import com.van.lily.module.system.service.impl.UserServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "用户列表")
@RequestMapping(ApiRouter.USER)
@RestController
public class UserController {

    @Autowired
    UserServiceImpl service;

    /**
    * 創建用戶
    */
    @PostMapping
    public AjaxResult pos(@RequestBody FormUser form) {
        return service.pos(form);
    }
}
