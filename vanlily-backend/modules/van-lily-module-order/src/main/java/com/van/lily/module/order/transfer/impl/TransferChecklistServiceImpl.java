package com.van.lily.module.order.transfer.impl;

import cn.hutool.json.JSONUtil;
import com.van.lily.commons.core.exception.QLogicException;
import com.van.lily.commons.define.enums.EnumBooleanTransfer;
import com.van.lily.commons.utils.qiong.QListUtil;
import com.van.lily.model.order.define.enums.EnumOrderMakeStatus;
import com.van.lily.model.order.entity.Order;
import com.van.lily.model.order.front.view.checklist.ViewCheckListItem;
import com.van.lily.model.order.front.view.order.jsonOrder.ViewOrderProduct;
import com.van.lily.model.order.front.view.order.jsonOrder.jsonProduct.ViewOrderProductRequire;
import com.van.lily.model.order.transfer.order.OrderProductTransfer;
import com.van.lily.model.order.transfer.order.OrderTransfer;
import com.van.lily.model.product.front.view.cake.jsonCake.ViewCakeSize;
import com.van.lily.model.product.front.view.cake.jsonCake.ViewCakeVariation;
import com.van.lily.model.product.front.view.cake.jsonCake.inner.ViewCakeVariationItem;
import com.van.lily.module.order.transfer.TransferChecklistService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransferChecklistServiceImpl implements TransferChecklistService {

    /**
    * 生成清单
    */
    public List<ViewCheckListItem> generateCheckList(Order order) {
        List<ViewCheckListItem> res = new ArrayList<>();
        // 取出所有蛋糕
        List<ViewOrderProduct> productList = OrderTransfer.jsonOrderProduct(order);
        productList = OrderProductTransfer.filterMainCakeList(productList);
        short index = 0;
        // 生成所有检查清单
        for (ViewOrderProduct op: productList) {

            // 1. 根据尺寸生成
            ViewCakeSize size = op.getSize();
            if (size == null) throw new QLogicException("該訂單的產品未選擇尺寸信息，親先完善訂單產品信息");
            res.add(generateCheckList(size, op.getId(), index));
            index += 1;

            // 2. 根据样式生成
            List<ViewCakeVariation> variationList = QListUtil.must(op.getVariations());
            for (ViewCakeVariation variation: variationList) {
                res.add(generateCheckList(variation, op.getId(), index));
                index += 1;
            }

            // 3. 根据蛋糕要求生成
            List<ViewOrderProductRequire> requireList = QListUtil.must(op.getRequires());
            for (ViewOrderProductRequire require: requireList) {
                res.add(generateCheckList(require, op.getId(), index));
                index += 1;
            }
        }

        // 增加 订单 ID
        res.forEach(c-> c.setOrder_id(order.getId()));
        return res;
    }

    /**
    * 同步檢查列表
    */
    public Order asyncChecklist(Order order, List<ViewCheckListItem> itemList) {

        // 是否已全部完成
        boolean isAllComplete = true;
        for (ViewCheckListItem ci: itemList) {
            if (EnumBooleanTransfer.isFalse(ci.getIs_complete()))
                isAllComplete = false;
        }

        // 檢測是否需要更改訂單 檢查狀態
        Short isCheckOld = order.getIs_check_complete();
        // 老狀態是 已完善檢查
        if (EnumBooleanTransfer.isTrue(isCheckOld)) {
            // 更改為未完成狀態
            if (!isAllComplete) {
                order.setIs_check_complete(EnumBooleanTransfer.FALSE.v);
            }
        }
        // 老狀態是 檢查不完善
        else {
            // 更改為已完成檢查
            if (isAllComplete) {
                order.setIs_check_complete(EnumBooleanTransfer.TRUE.v);
                // 更新完成檢查時間
                order.setChecking_end_time(new Date());
            }
        }

        // 同步訂單檢查清單
        order.setChecking_list(JSONUtil.toJsonStr(itemList));

        // 製作中的狀態
        order.setStatus_make(EnumOrderMakeStatus.make);

        return order;
    }


    /**
    * 生成检查清单
    */
    public ViewCheckListItem generateCheckList(ViewCakeSize size, Long productID, short idx) {
        ViewCheckListItem item = new ViewCheckListItem();
        item.setContent("重量：" + size.getG() + "，直徑：" + size.getDiameter() + "，描述：" + size.getText());
        item.setProduct_id(productID);
        item.setIndex(idx);
        item.setPublished(new Date());
        return item;
    }
    public ViewCheckListItem generateCheckList(ViewCakeVariation variation, Long productID, short idx) {
        List<ViewCakeVariationItem> itemList = variation.getItems();
        List<ViewCheckListItem> res = itemList.stream().map(v -> {
            ViewCheckListItem item = new ViewCheckListItem();
            item.setContent(variation.getText() + ": " + v.getText());
            item.setProduct_id(productID);
            item.setIndex(idx);
            item.setPublished(new Date());
            return item;
        }).collect(Collectors.toList());
        // 取出第一項 樣式 ITEM
        return QListUtil.isGoodList(res) ? res.get(0) : null;
    }
    public ViewCheckListItem generateCheckList(ViewOrderProductRequire require, Long productID, short idx) {
        ViewCheckListItem item = new ViewCheckListItem();
        item.setContent(require.getType_text() + "：" + require.getContent());
        item.setProduct_id(productID);
        item.setIndex(idx);
        item.setPublished(new Date());
        return item;
    }
}
