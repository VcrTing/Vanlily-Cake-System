package com.van.lily.model.order.transfer.order;

import cn.hutool.json.JSONUtil;
import com.van.lily.commons.utils.qiong.QBeanUtil;
import com.van.lily.commons.utils.qiong.QDateUtil;
import com.van.lily.commons.utils.qiong.QValueUtil;
import com.van.lily.model.order.define.enums.EnumOrderFrom;
import com.van.lily.model.order.entity.Order;
import com.van.lily.model.order.front.form.filling.jsonOrder.FormOrderDiscount;
import com.van.lily.model.order.front.form.reserve.FormReserveOrder;
import com.van.lily.model.order.front.view.order.jsonOrder.ViewOrderDiscount;
import com.van.lily.model.order.front.view.order.jsonOrder.ViewOrderProduct;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public final class OrderTransfer {
    final static String ID_PREFIX = "VAN";
    final static String ID_SUFFIX_NUMBER = "0000";

    /**
    * 可用订单
    */
    public static Boolean isGoodOrder(Order order) { return order != null && order.getId() != null; }
    public static Boolean isBadOrder(Order order) { return order == null || order.getId() == null; }

    /**
    * 生成底线时间，底线时间错误，则底线时间为 24 小时，24 小时内得付款
    */
    public static Date overDate(Date src) {
        if (QDateUtil.isAfter(src)) return src;
        return QDateUtil.afterHour(QDateUtil.HOUR_24);
    }

    /**
    * 生成订单 ID
    */
    public static String generateOrderID() {
        int num = QValueUtil.randomInt();
        String suffix = ID_SUFFIX_NUMBER + num;
        String tim = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        return ID_PREFIX + tim + suffix.substring(suffix.length() - ID_SUFFIX_NUMBER.length(), ID_SUFFIX_NUMBER.length());
    }

    /**
    * 生成默认订单数据
    */
    public static Order entityDefault() {
        Order res = new Order();
        res.setPublished(new Date());
        res.setUuid(generateOrderID());
        res = OrderStatusTransfer.resetNull(res);
        res.setBe_from(EnumOrderFrom.def());
        return res;
    }

    /**
    * 预约订单转订单
    */
    public static Order entityFromReserveParam(FormReserveOrder form) {
        Order res = entityDefault();
        res.setCashier_id(form.getCashier_id());
        if (form.getBe_from() != null) res.setBe_from(form.getBe_from());
        res.setIs_ahead(form.getIs_ahead());
        res.setDate_over(overDate(form.getDate_finally()));
        return res;
    }

    /**
    * 对蛋糕数据进行变化
    */
    public static String jsonOrderProduct(List<ViewOrderProduct> orderProductList) {
        return JSONUtil.toJsonStr(orderProductList);
    }
    public static List<ViewOrderProduct> jsonOrderProduct(Order order) {
        return JSONUtil.toList(QValueUtil.mustJsonList(order.getProducts()), ViewOrderProduct.class);
    }

    /**
    * Discount 轉換
    */
    public static ViewOrderDiscount discountTransfer(List<FormOrderDiscount> discountList) {
        return QBeanUtil.convert(discountList, ViewOrderDiscount.class);
    }
}
