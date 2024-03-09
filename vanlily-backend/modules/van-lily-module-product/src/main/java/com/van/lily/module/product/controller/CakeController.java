package com.van.lily.module.product.controller;

import com.van.lily.commons.core.AjaxResult;
import com.van.lily.commons.core.AjaxResultUtil;
import com.van.lily.model.product.front.form.cake.FormCake;
import com.van.lily.module.product.common.ApiRouter;
import com.van.lily.module.product.service.base.impl.CakeServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping(ApiRouter.CAKE)
@Api(tags = "蛋糕列表")
@RestController
public class CakeController {

    @Autowired
    CakeServiceImpl service;

    /**
    * 深度列表
    */
    @GetMapping
    public AjaxResult pag() {
        return service.pag(null);
    }

    /**
    * 深度详情
    */
    @GetMapping(ApiRouter.ID)
    public AjaxResult one(@PathVariable Long id) {
        return AjaxResultUtil.success(id);
    }

    /**
    * 新增蛋糕
    */
    @PostMapping
    public AjaxResult pos(@RequestBody FormCake form) { return service.pos(form); }
}
