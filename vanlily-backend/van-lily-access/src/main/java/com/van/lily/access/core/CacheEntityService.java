package com.van.lily.access.core;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.van.lily.commons.keys.cache.KeySystemConstants;
import com.van.lily.commons.utils.FormTextUtil;
import com.van.lily.commons.utils.qiong.QListUtil;
import com.van.lily.commons.utils.qiong.QValueUtil;
import com.van.lily.framework.core.tools.RedisTool;
import com.van.lily.model.system.entity.Customer;
import com.van.lily.model.system.entity.User;
import com.van.lily.model.system.mapper.entity.CustomerMapper;
import com.van.lily.model.system.mapper.entity.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CacheEntityService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    CustomerMapper customerMapper;

    @Autowired
    RedisTool redisTool;

    /**
    * 獲取用戶，經過緩存
    */
    public User getUser(String username) {
        if (QValueUtil.hasNoLength(username)) return null;
        User res = null;
        // 從緩存中獲取
        if (!FormTextUtil.isEmail(username))
            res = redisTool.getObject(KeySystemConstants.USER_BY_PHONE + username);
        // 從數據庫中獲取
        if (res == null) res = getUserFromStore(username);
        // 存入緩存
        if (res != null) {
            redisTool.setObject(KeySystemConstants.USER_BY_PHONE + res.getPhone(), res);
            if (QValueUtil.hasLength(res.getEmail()))
                redisTool.setObject(KeySystemConstants.USER_BY_EMAIL + res.getEmail(), res);
        }
        // 返回
        return res;
    }
    public Customer getCustomer(String username) {
        if (QValueUtil.hasNoLength(username)) return null;
        Customer res = null;
        // 從緩存中獲取
        if (FormTextUtil.isEmail(username)) {
            res = redisTool.getObject(KeySystemConstants.ONE_BY_EMAIL + username);
        } else {
            res = redisTool.getObject(KeySystemConstants.ONE_BY_PHONE + username);
        }
        // 從數據庫中獲取
        if (res == null) res = getCustomerFromStore(username);
        // 存入緩存
        if (res != null) {
            redisTool.setObject(KeySystemConstants.ONE_BY_PHONE + res.getPhone(), res);
            if (QValueUtil.hasLength(res.getEmail()))
                redisTool.setObject(KeySystemConstants.ONE_BY_EMAIL + res.getEmail(), res);
        }
        // 返回
        return res;
    }

    /**
    * 獲取 USER，從數據庫中
    */
    public User getUserFromStore(String username) {
        LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<>();
        qw.eq(FormTextUtil.isEmail(username) ? User::getEmail : User::getPhone, username);
        List<User> srcList = userMapper.selectList(qw);
        if (QListUtil.isGoodList(srcList))
            for (User src: srcList)
                if (username.equals(src.getPhone()) || username.equals(src.getEmail())) return src;
        return null;
    }
    public Customer getCustomerFromStore(String username) {
        LambdaQueryWrapper<Customer> qw = new LambdaQueryWrapper<>();
        qw.eq(FormTextUtil.isEmail(username) ? Customer::getEmail : Customer::getPhone, username);
        List<Customer> srcList = customerMapper.selectList(qw);
        if (QListUtil.isGoodList(srcList))
            for (Customer src: srcList)
                if (username.equals(src.getEmail()) || username.equals(src.getPhone())) return src;
        return null;
    }
}
