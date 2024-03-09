package com.van.lily.module.order.bridge.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.van.lily.commons.core.exception.QCloudException;
import com.van.lily.commons.keys.cache.KeySystemConstants;
import com.van.lily.commons.utils.qiong.QValueUtil;
import com.van.lily.framework.core.tools.RedisTool;
import com.van.lily.model.system.entity.Customer;
import com.van.lily.model.system.mapper.entity.CustomerMapper;
import com.van.lily.model.system.transfer.CustomerTransfer;
import com.van.lily.module.order.bridge.BridgeCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BridgeCustomerServiceImpl implements BridgeCustomerService {

    @Autowired
    CustomerMapper mapper;

    @Autowired
    RedisTool redisTool;

    /**
    * 根据电话号码获取客人
    */
    public Customer one(Long id) {
        String key = KeySystemConstants.ONE_BY_ID + id;
        Customer res = redisTool.getObject(key);
        if (res == null) {
            res = mapper.selectById(id);
            if (res != null) {
                saveToCache(res);
                redisTool.expire(key);
            }
        } else { redisTool.expire(key); }
        if (res == null) throw new QCloudException("客人數據為空，請檢查該客人是否存在。");
        return res;
    }
    public Customer one(String phone) {
        LambdaQueryWrapper<Customer> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Customer::getPhone, phone);
        return mapper.selectOne(queryWrapper);
    }

    public Customer oneByPhone(String phone) {
        String key = KeySystemConstants.ONE_BY_PHONE + phone;
        Customer res = redisTool.getObject(key);
        if (res == null) {
            res = one(phone);
            if (res != null) {
                saveToCache(res);
                redisTool.expire(key);
            }
        } else { redisTool.expire(key); }
        if (res == null) throw new QCloudException("客人數據為空，請檢查該客人是否存在。");
        return res;
    }

    /**
    * 将客人存入缓存之中
    */
    public void saveToCache(Customer customer) {
        if (customer != null && customer.getId() != null) {
            // 根据 ID
            redisTool.setObject(KeySystemConstants.ONE_BY_ID + customer.getId(), customer);
            // 根据 电话号码
            redisTool.setObject(KeySystemConstants.ONE_BY_PHONE + customer.getPhone(), customer);
        }
    }

    /**
    * 创建默认用户
    */
    public Customer posSimply(String phone, String name) {
        Customer customer = CustomerTransfer.generateSimply(phone, name);
        int res = mapper.insert(customer);
        return (res > 0) ? customer : null;
    }

    /**
    * 获取一个 客人，或者创建客人
    */
    public Customer oneOrCreat(String phone, String name) {
        if (QValueUtil.hasNoLength(phone)) return null;
        // 先从缓存中获取
        String key = KeySystemConstants.ONE_BY_PHONE + phone;
        Customer result = redisTool.getObject(key);
        // 若缓存内的为空
        if (result == null) {
            // 数据库中获取
            phone = QValueUtil.serString(phone);
            result = oneByPhone(phone);
            // 若数据库内没有
            if (result == null) {
                // 创建一个新用户
                result = posSimply(phone, QValueUtil.serString(name));
            }
            // 存入缓存
            saveToCache(result);
        }
        if (result == null) throw new QCloudException("因網絡波動，無法查找或新增到客人數據");
        return result;
    }
}
