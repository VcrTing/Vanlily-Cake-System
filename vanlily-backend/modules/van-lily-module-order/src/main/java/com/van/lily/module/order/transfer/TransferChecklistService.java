package com.van.lily.module.order.transfer;

import com.van.lily.model.order.entity.Order;
import com.van.lily.model.order.front.view.checklist.ViewCheckListItem;

import java.util.List;

public interface TransferChecklistService {

    /**
    * 同步檢查清單、檢查狀態
    */
    Order asyncChecklist(Order order, List<ViewCheckListItem> itemList);
}
