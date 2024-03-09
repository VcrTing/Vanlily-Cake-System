package com.van.lily.module.order.controller.inwork;

import com.van.lily.commons.core.AjaxResult;
import com.van.lily.commons.core.AjaxResultUtil;
import com.van.lily.module.order.common.ApiRouter;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

@RequestMapping(ApiRouter.ORDER_PRODUCT)
@Api(tags = "订单列表")
@RestController
public class OrderProductController {

    /**
    * 添加蛋糕信息
    */
    @PostMapping(ApiRouter.ID)
    public AjaxResult add(@PathVariable Long id) {
        return AjaxResultUtil.success(1);
    }

    /**
    * 完善蛋糕信息
    */
    @PatchMapping(ApiRouter.ID)
    public AjaxResult upd(@PathVariable Long id) {
        return AjaxResultUtil.success(1);
    }

    /**
    * 刪除蛋糕信息
    */
    @DeleteMapping(ApiRouter.ID)
    public AjaxResult del(@PathVariable Long id) {
        return AjaxResultUtil.success(1);
    }
}
