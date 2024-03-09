package com.van.lily.model.order.transfer.remind;

import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import com.van.lily.commons.define.enums.EnumBooleanTransfer;
import com.van.lily.commons.utils.qiong.QDateUtil;
import com.van.lily.commons.utils.qiong.QValueUtil;
import com.van.lily.model.order.entity.remind.OrderRemind;
import com.van.lily.model.order.front.group.GroupOrderRemindForm;
import com.van.lily.model.order.front.view.remind.ViewRemindData;
import com.van.lily.model.order.front.view.remind.ViewRemindTo;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public final class RemindTransfer {
    /**
    * 是否是个正常的 提醒时间
    */
    public static boolean isGoodTime(Date remindTime) {
        return remindTime != null && QDateUtil.isAfter(remindTime);
    }

    /**
    * 獲取 ID
    */
    public static Long getOrderID(OrderRemind remind) {
        Long res = null;
        ViewRemindData data = JSONUtil.toBean(QValueUtil.mustJsonBean(remind.getRemind_data()), ViewRemindData.class);
        if (data == null) log.error("數據為空，remind.getRemind_data() JSON 轉換為空，地點：RemindTransfer.getOrderID");
        else {
            res = data.getOrder_id();
        }
        if (res == null) log.error("數據為空，未找到 Order ID，地點：RemindTransfer.getOrderID");
        return res;
    }

    public static Long getToID(OrderRemind remind, Boolean isCustomer) {
        Long res = null;
        ViewRemindTo to = JSONUtil.toBean(QValueUtil.mustJsonBean(remind.getRemind_to()), ViewRemindTo.class);
        if (to == null) log.error("數據為空，remind.getRemind_to() JSON 轉換為空，地點：RemindTransfer.getToID");
        else {
            res = isCustomer ? to.getMember_id() : to.getCashier_id();
            if (res == null) log.error("數據為空，未找到 " + (isCustomer ? "customer" : "cashier") + " ID，地點：RemindTransfer.getToID");
        }
        return res;
    }

    /**
    * 根据表格生成 Remind，GroupOrderRemindForm 只是一個內部數據
    */
    public static OrderRemind entityByForm(GroupOrderRemindForm form) {
        OrderRemind res = new OrderRemind();
        if (form.getTo() != null)
            res.setRemind_to(JSONUtil.toJsonStr(form.getTo()));
        if (form.getData() != null)
            res.setRemind_data(JSONUtil.toJsonStr(form.getData()));
        res.setRemind_time(form.getRemindTime());
        res.setPublished(new Date());
        res.setType_to(form.getToType());
        res.setType_work(form.getWorkType());
        res.setIs_over(EnumBooleanTransfer.FALSE.v);
        res.setIs_stop(EnumBooleanTransfer.FALSE.v);
        return res;
    }
}
