package com.van.lily.module.order.cache.impl;

import cn.hutool.json.JSONUtil;
import com.van.lily.commons.core.exception.QCloudException;
import com.van.lily.commons.core.exception.QLogicException;
import com.van.lily.commons.keys.cache.KeyOrderConstants;
import com.van.lily.commons.utils.qiong.QListUtil;
import com.van.lily.commons.utils.qiong.QValueUtil;
import com.van.lily.framework.core.tools.RedisTool;
import com.van.lily.model.order.entity.Order;
import com.van.lily.model.order.front.view.checklist.ViewCheckListItem;
import com.van.lily.model.order.mapper.dao.DaoOrderMapper;
import com.van.lily.model.order.mapper.entity.OrderMapper;
import com.van.lily.model.order.transfer.order.OrderTransfer;
import com.van.lily.module.order.cache.CacheOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class CacheOrderServiceImpl implements CacheOrderService {

    @Autowired
    RedisTool redisTool;

    @Autowired
    OrderMapper mapper;

    @Autowired
    DaoOrderMapper daoOrderMapper;

    /**
    * 获取 ONE ORDER
    */
    public Order one(Long id) {
        String key = KeyOrderConstants.ORDER_BY_ID + id;
        Order res = redisTool.getObject(key);
        if (res == null) {
            res = mapper.selectById(id);
            if (res != null) {
                saveToCache(res);
                redisTool.expire(key);
            }
        } else { redisTool.expire(key); }
        if (OrderTransfer.isBadOrder(res)) throw new QCloudException("訂單數據為空，請檢查該訂單是否存在。");
        return res;
    }
    /**
    * 新增
    */
    public Order pos(Order entity) {
        if (entity == null) throw new QCloudException("訂單數據為空，請檢查該訂單是否存在。");
        entity.setDate_update(new Date());
        int res = mapper.insert(entity);
        if (res > 0) { saveToCache(entity); return entity; }
        throw new QLogicException("因網絡波動，無法新增訂單數據，本次新增訂單失敗");
    }

    /**
    * 修改订单数据
    */
    public Order upd(Order entity) {
        if (OrderTransfer.isBadOrder(entity)) throw new QCloudException("訂單數據為空，請檢查該訂單是否存在。");
        entity.setDate_update(new Date());
        int res = mapper.updateById(entity);
        if (res > 0) { saveToCache(entity); return entity; }
        throw new QLogicException("因網絡波動，無法同步完善訂單數據，本次訂單信息完善失敗");
    }

    // 存入緩存
    public void saveToCache(Order src) {
        if (OrderTransfer.isGoodOrder(src)) {
            String key = KeyOrderConstants.ORDER_BY_ID + src.getId();
            redisTool.setObject(key, src);
        }
    }

    /**
    * 检查清单
    */
    public void saveCheckList(List<ViewCheckListItem> src, Long orderID) {
        if (QListUtil.isGoodList(src)) {
            String key = KeyOrderConstants.CHECK_LIST_BY_ID + orderID;
            redisTool.setObject(key, src);
        }
    }
    public void saveCheckList(Order order, List<ViewCheckListItem> src) {
        if (QListUtil.isGoodList(src)) {
            String key = KeyOrderConstants.CHECK_LIST_BY_ID + order.getId();
            redisTool.setObject(key, src);
            saveToCache(order);
        }
    }
    public List<ViewCheckListItem> getCheckList(Long orderID) {
        // 优先缓存中获取
        String key = KeyOrderConstants.CHECK_LIST_BY_ID + orderID;
        List<ViewCheckListItem> res = redisTool.getObject(key);
        if (QListUtil.isBadList(res)) {

            // 缓存里面没有，就从 ORDER 里面拿
            Order order = one(orderID);
            String resString = order.getChecking_list();

            if (QValueUtil.hasLength(resString)) {
                res = JSONUtil.toList(QValueUtil.mustJsonList(resString), ViewCheckListItem.class);
            }
        }
        return res;
    }
    public List<ViewCheckListItem> getCheckList(Order order) {
        // 優先從訂單裡面拿
        String itemsString = order.getChecking_list();
        if (QValueUtil.hasNoLength(itemsString)) throw new QLogicException("未發現該訂單有檢查清單信息，請檢查訂單信息的完整性");
        // 轉化
        List<ViewCheckListItem> itemList = JSONUtil.toList(QValueUtil.mustJsonList(itemsString), ViewCheckListItem.class);
        if (QListUtil.isBadList(itemList)) throw new QLogicException("未發現該訂單有檢查清單信息，請檢查訂單信息的完整性");
        // 同步至 緩存
        saveCheckList(itemList, order.getId());
        // 返回
        return itemList;
    }
}
