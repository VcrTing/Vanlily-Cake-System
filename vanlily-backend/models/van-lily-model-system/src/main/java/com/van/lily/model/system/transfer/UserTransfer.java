package com.van.lily.model.system.transfer;

import cn.hutool.json.JSONUtil;
import com.van.lily.commons.core.exception.QLogicException;
import com.van.lily.commons.define.text.DefaultConstants;
import com.van.lily.commons.utils.qiong.QBeanUtil;
import com.van.lily.model.system.define.enums.EnumCustomerFrom;
import com.van.lily.model.system.define.enums.EnumCustomerGender;
import com.van.lily.model.system.entity.Customer;
import com.van.lily.model.system.entity.User;
import com.van.lily.model.system.front.form.user.FormUser;
import com.van.lily.model.system.front.view.customer.jsonCustomer.ViewCustomerBeFrom;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class UserTransfer {
    /**
    * 加入一个新的 用戶
    */
    public static User entityByForm(FormUser form) {
        User user = QBeanUtil.convert(form, User.class);
        if (user == null) throw new QLogicException("用戶表單數據格式錯誤");
        // 返回数据
        return user;
    }

}
