package com.van.lily.module.order.cache;

import com.van.lily.model.order.entity.Order;

public interface CacheOrderService {

    /**
    * 获取 one order
    */
    Order one(Long id);

    /**
    * 新增 one order
    */
    Order pos(Order entity);

    /**
    * 修改 one order
    */
    Order upd(Order entity);

    /**
    * 储存 检查清单，不同步訂單
    */
    // void saveCheckList(List<ViewCheckItem> src, Long orderID);
    /**
    * 儲存 檢查清單，同步訂單數據
    */
    // void saveCheckList(Order order, List<ViewCheckItem> src);
    /**
    * 取出 检查清单，會經過 數據庫
    */
    // List<ViewCheckItem> getCheckList(Long orderID);
    /**
     * 取出 检查清单，會經過 數據庫
     */
    // List<ViewCheckItem> getCheckList(Order order);
}
