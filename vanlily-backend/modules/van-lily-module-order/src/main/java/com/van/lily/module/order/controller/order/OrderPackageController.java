package com.van.lily.module.order.controller.order;

import com.van.lily.commons.core.AjaxResult;
import com.van.lily.module.order.common.ApiRouter;
import com.van.lily.module.order.service.dealing.impl.PackageServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping(ApiRouter.PACKAGE)
@Api(tags = "订单打包")
@RestController
public class OrderPackageController {

    @Autowired
    PackageServiceImpl service;

    /**
    * 订单已打包完成，随时准备自提、或者等待時間進行配送
    */
    @PatchMapping(ApiRouter.ID)
    public AjaxResult upd(@PathVariable Long id) {
        return service.upd(id);
    }

}
