package com.van.lily.module.order.service.remind;

import com.van.lily.model.order.entity.remind.OrderRemind;
import com.van.lily.model.order.front.group.GroupOrderRemindForm;

import java.util.List;

public interface RemindGenerateService {
    /**
    * 生成订单提醒
    */
    void generateOrderRemind(GroupOrderRemindForm form);
}
