package com.van.lily.model.order.transfer.remind;

import com.van.lily.model.order.define.enums.EnumRemindOrderType;
import com.van.lily.model.order.define.enums.EnumRemindToType;
import com.van.lily.model.order.entity.Order;
import com.van.lily.model.order.front.group.GroupOrderRemindForm;
import com.van.lily.model.order.front.view.remind.ViewRemindData;
import com.van.lily.model.order.front.view.remind.ViewRemindTo;

import java.util.Date;

public final class RemindGroupFormTransfer {

    /**
    * 工具
    */
    static ViewRemindTo generateTo(Long customerID, Long cashierID) {
        ViewRemindTo to = new ViewRemindTo();
        to.setMember_id(customerID);
        to.setCashier_id(cashierID);
        return to;
    }
    static ViewRemindData generateData(Order order) {
        ViewRemindData data = new ViewRemindData();
        data.setOrder_id(order.getId());
        return data;
    }
    static GroupOrderRemindForm entityMessage(Order order, Date remindTime, EnumRemindOrderType workType) {
        GroupOrderRemindForm form = new GroupOrderRemindForm();
        form.setToType(EnumRemindToType.message);
        form.setWorkType(workType);
        form.setRemindTime(remindTime);
        form.setData(generateData(order));
        return form;
    }

    /**
    * 集中方法
    */
    public static GroupOrderRemindForm generate(Order order, Long toID, Date remindTime, EnumRemindOrderType workType) {
        switch (workType) {
            case pay_not_yet_for_customer:
                return payNotCustomer(remindTime, toID, order);
            case pay_not_yet_for_cashier:
                return payNotCashier(remindTime, toID, order);
            case paid_for_customer:
                return paidCustomer(remindTime, toID, order);
            case paid_for_cashier:
                return paidCashier(remindTime, toID, order);
            case packaged_for_customer:
                return packagedCustomer(remindTime, toID, order);
            case packaged_for_cashier:
                return packagedCashier(remindTime, toID, order);
        }
        return null;
    }

    /**
    * pay_not_yet_for_customer
    */
    static GroupOrderRemindForm payNotCustomer(Date remindTime, Long customerID, Order order) {
        GroupOrderRemindForm form = entityMessage(order, remindTime, EnumRemindOrderType.pay_not_yet_for_customer);
        form.setTo(generateTo(customerID, null));
        return form;
    }
    /**
    * pay_not_yet_for_cashier
    */
    static GroupOrderRemindForm payNotCashier(Date remindTime, Long cashierID, Order order) {
        GroupOrderRemindForm form = entityMessage(order, remindTime, EnumRemindOrderType.pay_not_yet_for_cashier);
        form.setTo(generateTo(null, cashierID));
        return form;
    }
    /**
    * paid_for_customer
    */
    static GroupOrderRemindForm paidCustomer(Date remindTime, Long customerID, Order order) {
        GroupOrderRemindForm form = entityMessage(order, remindTime, EnumRemindOrderType.paid_for_customer);
        form.setTo(generateTo(customerID, null));
        return form;
    }
    /**
    * paid_for_cashier
    */
    static GroupOrderRemindForm paidCashier(Date remindTime, Long cashierID, Order order) {
        GroupOrderRemindForm form = entityMessage(order, remindTime, EnumRemindOrderType.paid_for_cashier);
        form.setTo(generateTo(null, cashierID));
        return form;
    }
    /**
    * packaged_for_customer
    */
    static GroupOrderRemindForm packagedCustomer(Date remindTime, Long customerID, Order order) {
        GroupOrderRemindForm form = entityMessage(order, remindTime, EnumRemindOrderType.packaged_for_customer);
        form.setTo(generateTo(customerID, null));
        return form;
    }
    /**
    * packaged_for_cashier
    */
    static GroupOrderRemindForm packagedCashier(Date remindTime, Long cashierID, Order order) {
        GroupOrderRemindForm form = entityMessage(order, remindTime, EnumRemindOrderType.packaged_for_cashier);
        form.setTo(generateTo(null, cashierID));
        return form;
    }
}
