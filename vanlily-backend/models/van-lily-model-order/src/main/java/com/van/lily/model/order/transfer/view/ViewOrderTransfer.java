package com.van.lily.model.order.transfer.view;

import cn.hutool.json.JSONUtil;
import com.van.lily.commons.utils.qiong.QBeanUtil;
import com.van.lily.commons.utils.qiong.QValueUtil;
import com.van.lily.model.order.dao.order.DaoOrder;
import com.van.lily.model.order.front.view.order.ViewOrder;
import com.van.lily.model.order.front.view.order.jsonMan.ViewOrderCharge;
import com.van.lily.model.order.front.view.order.jsonOrder.ViewOrderDiscount;
import com.van.lily.model.order.front.view.order.jsonOrder.ViewOrderPayment;
import com.van.lily.model.order.front.view.order.jsonOrder.ViewOrderProduct;
import com.van.lily.model.order.front.view.order.jsonOrder.ViewOrderRemark;
import com.van.lily.model.order.front.view.order.jsonRefund.ViewOrderRefund;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public final class ViewOrderTransfer {

    public static ViewOrder transfer(DaoOrder daoOrder) {
        ViewOrder view = QBeanUtil.convert(daoOrder, ViewOrder.class);
        if (view == null) {
            log.error("轉換異常: ORDER DAO TO VIEW ERROR，Method: ViewOrderTransfer.transfer");
            return null;
        }
        view.setRemarks(JSONUtil.toList(QValueUtil.mustJsonList(daoOrder.getRemarks()), ViewOrderRemark.class));
        view.setPayments(JSONUtil.toList(QValueUtil.mustJsonList(daoOrder.getPayments()), ViewOrderPayment.class));

        view.setProducts(JSONUtil.toList(QValueUtil.mustJsonList(daoOrder.getProducts()), ViewOrderProduct.class));
        view.setDiscounts(JSONUtil.toList(QValueUtil.mustJsonList(daoOrder.getDiscounts()), ViewOrderDiscount.class));

        view.setRefund(JSONUtil.toBean(QValueUtil.mustJsonBean(daoOrder.getRefund()), ViewOrderRefund.class));
        view.setCharge_checkout(JSONUtil.toBean(QValueUtil.mustJsonBean(daoOrder.getCharge_checkout()), ViewOrderCharge.class));

        return view;
    }

    public static List<ViewOrder> transfers(List <DaoOrder> src) {
        return src.stream().map(ViewOrderTransfer::transfer).filter(Objects::nonNull).collect(Collectors.toList());
    }
}
