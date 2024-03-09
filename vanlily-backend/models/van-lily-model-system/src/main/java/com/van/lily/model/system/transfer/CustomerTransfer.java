package com.van.lily.model.system.transfer;

import cn.hutool.json.JSONUtil;
import com.van.lily.commons.define.text.DefaultConstants;
import com.van.lily.model.system.define.enums.EnumCustomerFrom;
import com.van.lily.model.system.define.enums.EnumCustomerGender;
import com.van.lily.model.system.entity.Customer;
import com.van.lily.model.system.front.view.customer.jsonCustomer.ViewCustomerBeFrom;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class CustomerTransfer {
    /**
    * 加入一个新的 客户来源
    */
    public static String addBeFrom(List<ViewCustomerBeFrom> origin, EnumCustomerFrom beFrom) {
        // 构建数据
        ViewCustomerBeFrom src = new ViewCustomerBeFrom();
        src.setBe_from(beFrom);
        src.setPublished(new Date());
        src.setRemark(DefaultConstants.REMARK_DEFAULT);
        // 加入列表
        List<ViewCustomerBeFrom> result = origin != null ? origin : new ArrayList<>();
        result.add(src);
        // 返回数据
        return JSONUtil.toJsonStr(result);
    }

    /**
    * 根据 电话号码 生成简单客人
    */
    public static Customer generateSimply(String phone, String name) {
        Customer customer = new Customer();
        customer.setPhone(phone);
        customer.setName(name);
        customer.setNickname(name);
        customer.setPublished(new Date());
        // 此密码为空
        customer.setPassword(null);
        customer.setGender(EnumCustomerGender.def());
        customer.setBe_froms(addBeFrom(null, EnumCustomerFrom.web));
        return customer;
    }
}
