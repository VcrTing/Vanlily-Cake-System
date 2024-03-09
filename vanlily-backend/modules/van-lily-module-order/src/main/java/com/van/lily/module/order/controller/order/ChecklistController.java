package com.van.lily.module.order.controller.order;

import com.van.lily.commons.core.AjaxResult;
import com.van.lily.model.order.front.view.checklist.ViewCheckListItem;
import com.van.lily.module.order.common.ApiRouter;
import com.van.lily.module.order.service.dealing.impl.ChecklistServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping(ApiRouter.CHECKLIST)
@Api(tags = "验核清单")
@RestController
public class ChecklistController {

    @Autowired
    ChecklistServiceImpl service;

    /**
    * 查询某订单产品清单
    */
    @GetMapping(ApiRouter.ID + ApiRouter.PID)
    public AjaxResult list(@PathVariable("id") Long id, @PathVariable("pid") Long pid) {
        return service.list(id, pid);
    }

    /**
    * 修改一項清單
    */
    @PatchMapping(ApiRouter.ID)
    public AjaxResult upd(@PathVariable("id") Long id, @RequestBody ViewCheckListItem item) {
        return service.updItem(id, item.getIndex(), item);
    }
}
