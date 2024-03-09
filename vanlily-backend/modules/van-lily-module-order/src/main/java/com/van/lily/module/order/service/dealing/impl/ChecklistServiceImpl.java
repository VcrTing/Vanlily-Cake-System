package com.van.lily.module.order.service.dealing.impl;

import com.van.lily.commons.core.AjaxResult;
import com.van.lily.commons.core.AjaxResultUtil;
import com.van.lily.commons.define.enums.EnumBooleanTransfer;
import com.van.lily.commons.utils.qiong.QListUtil;
import com.van.lily.model.order.entity.Order;
import com.van.lily.model.order.front.view.checklist.ViewCheckListItem;
import com.van.lily.model.order.mapper.entity.OrderMapper;
import com.van.lily.model.order.transfer.order.OrderStatusTransfer;
import com.van.lily.module.order.cache.impl.CacheOrderServiceImpl;
import com.van.lily.module.order.service.dealing.ChecklistService;
import com.van.lily.module.order.transfer.impl.TransferChecklistServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ChecklistServiceImpl implements ChecklistService {

    @Autowired
    TransferChecklistServiceImpl transferChecklistService;

    @Autowired
    CacheOrderServiceImpl cacheOrderService;

    @Autowired
    OrderMapper mapper;

    /**
    * 查詢 該產品的 檢查清單
    */
    public AjaxResult list(Long orderID, Long productID) {
        List<ViewCheckListItem> itemList = cacheOrderService.getCheckList(orderID);
        if (QListUtil.isBadList(itemList)) return AjaxResultUtil.error("該訂單還未有檢查清單，請先訂單數據的完善性");
        List<ViewCheckListItem> result = itemList.stream()
                .filter(i -> i.getProduct_id().equals(productID))
                .collect(Collectors.toList());
        if (QListUtil.isBadList(result)) return AjaxResultUtil.error("該訂單產品未有檢查清單，請檢查該產品的信息");
        return AjaxResultUtil.success(itemList);
    }

    /**
    * 修改某一項檢查清單
    * index 是核心定位值
    */
    public AjaxResult updItem(Long orderID, Short index, ViewCheckListItem item) {
        // 檢測
        if (index == null || index < 0)
            return AjaxResultUtil.error("未檢測到可用的清單索引值，親檢查清單索引值");
        if (!EnumBooleanTransfer.isNiceOr(item.getIs_out_stock(), item.getIs_complete()))
            return AjaxResultUtil.error("未檢測到合理的 SHORT布尔值，親檢查布尔值是否格式正確");
        // 先獲取訂單
        Order order = cacheOrderService.one(orderID);
        if (OrderStatusTransfer.canChecklist(order)) {

            // 先獲取所有清單
            List<ViewCheckListItem> itemList = cacheOrderService.getCheckList(order);
            // 修改其中的
            for (ViewCheckListItem ci: itemList) {
                if (ci.getIndex().equals(index)) {
                    // 修改完成
                    ci.setIs_complete(item.getIs_complete());
                    // 修改出貨
                    ci.setIs_out_stock(item.getIs_out_stock());
                    // 完成時間
                    if (EnumBooleanTransfer.isTrues(ci.getIs_out_stock(), ci.getIs_complete()))
                        ci.setComplete_time(new Date());
                    else
                        ci.setComplete_time(null);
                }
            }
            // 同步訂單清單結果
            order = transferChecklistService.asyncChecklist(order, itemList);
            // 修改
            order = cacheOrderService.upd(order);
            // 修改之後，同步檢查清單至緩存
            cacheOrderService.saveCheckList(order, itemList);
            // 返回
            return AjaxResultUtil.success(itemList);
        }
        return AjaxResultUtil.error("訂單狀態異常，無法進行訂單清單的修改");
    }
}
