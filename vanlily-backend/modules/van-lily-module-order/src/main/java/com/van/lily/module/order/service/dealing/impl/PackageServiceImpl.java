package com.van.lily.module.order.service.dealing.impl;

import com.van.lily.commons.core.AjaxResult;
import com.van.lily.commons.core.AjaxResultUtil;
import com.van.lily.model.order.define.enums.EnumOrderMakeStatus;
import com.van.lily.model.order.define.enums.EnumRemindOrderType;
import com.van.lily.model.order.entity.Order;
import com.van.lily.model.order.mapper.dao.DaoOrderMapper;
import com.van.lily.model.order.mapper.entity.OrderMapper;
import com.van.lily.model.order.transfer.order.OrderStatusTransfer;
import com.van.lily.model.order.transfer.view.ViewOrderTransfer;
import com.van.lily.module.order.cache.impl.CacheOrderServiceImpl;
import com.van.lily.module.order.service.dealing.PackageService;
import com.van.lily.module.order.service.remind.impl.RemindGenerateServiceImpl;
import com.van.lily.module.order.service.remind.impl.RemindStopServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PackageServiceImpl implements PackageService {

    @Autowired
    RemindGenerateServiceImpl remindGenerateService;
    @Autowired
    RemindStopServiceImpl remindStopService;
    @Autowired
    CacheOrderServiceImpl cacheOrderService;
    @Autowired
    OrderMapper mapper;
    @Autowired
    DaoOrderMapper daoOrderMapper;

    /**
    * 打包完成
    */
    public AjaxResult upd(Long orderID) {
        // 获取一个订单
        Order order = cacheOrderService.one(orderID);
        // 打包
        if (OrderStatusTransfer.canPackage(order)) {
            // 更改狀態
            order.setStatus_make(EnumOrderMakeStatus.packaged);
            order.setDate_packaged(new Date());
            // 修改
            order = cacheOrderService.upd(order);
            // 生成提醒
            remindGenerateService.generate(order, EnumRemindOrderType.packaged_for_cashier);
            remindGenerateService.generate(order, EnumRemindOrderType.packaged_for_customer);
            // 暂停一些提醒
            remindStopService.stopOrderRemind(order.getId());
            // 返回
            return AjaxResultUtil.success(ViewOrderTransfer.transfer(daoOrderMapper.oneDeep(orderID)));
        }
        return AjaxResultUtil.error("客人信息錯誤，完成打包失敗，請先檢查客人信息完整性");
    }
}
