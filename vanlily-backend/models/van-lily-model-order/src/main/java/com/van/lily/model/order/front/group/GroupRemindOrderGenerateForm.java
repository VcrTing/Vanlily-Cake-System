package com.van.lily.model.order.front.group;

import com.van.lily.model.order.define.enums.EnumRemindOrderType;
import com.van.lily.model.order.define.enums.EnumRemindToType;
import com.van.lily.model.order.entity.Order;
import com.van.lily.model.order.front.view.remind.ViewRemindData;
import com.van.lily.model.order.front.view.remind.ViewRemindTo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupRemindOrderGenerateForm {
    private Order order;
    private EnumRemindOrderType workType;
}
