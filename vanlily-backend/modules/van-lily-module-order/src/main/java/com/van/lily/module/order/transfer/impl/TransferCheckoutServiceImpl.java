package com.van.lily.module.order.transfer.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.van.lily.commons.config.VanLilyConfigure;
import com.van.lily.commons.core.exception.QLogicException;
import com.van.lily.commons.define.enums.EnumBooleanTransfer;
import com.van.lily.commons.utils.qiong.QDateUtil;
import com.van.lily.commons.utils.qiong.QSumUtil;
import com.van.lily.commons.utils.qiong.QValueUtil;
import com.van.lily.model.order.entity.Order;
import com.van.lily.model.order.front.form.filling.jsonOrder.FormChargeSimply;
import com.van.lily.model.order.front.form.filling.jsonOrder.FormOrderDiscount;
import com.van.lily.model.order.front.form.filling.jsonOrder.FormOrderRemark;
import com.van.lily.model.order.front.form.filling.jsonProduct.FormFillingOrderProduct;
import com.van.lily.model.order.front.form.filling.jsonProduct.inner.FormFillingProductRequire;
import com.van.lily.model.order.front.form.payment.jsonOrder.FormOrderPayment;
import com.van.lily.model.order.front.form.reserve.jsonOrder.FormReserveOrderProduct;
import com.van.lily.model.order.front.view.order.jsonOrder.ViewOrderProduct;
import com.van.lily.model.order.mapper.entity.OrderMapper;
import com.van.lily.model.order.transfer.order.OrderProductTransfer;
import com.van.lily.model.order.transfer.order.OrderTransfer;
import com.van.lily.model.product.entity.Cake;
import com.van.lily.model.product.front.view.cake.jsonCake.ViewCakeSize;
import com.van.lily.model.product.front.view.cake.jsonCake.ViewCakeVariation;
import com.van.lily.module.order.bridge.impl.BridgeCakeServiceImpl;
import com.van.lily.module.order.transfer.TransferCheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransferCheckoutServiceImpl implements TransferCheckoutService {

    @Autowired
    BridgeCakeServiceImpl bridgeCakeService;

    @Autowired
    OrderMapper mapper;

    final static String CHAR_A = "A";

    /**
    * 金额差值对比
    */
    public Boolean lessThanLimit(BigDecimal one, BigDecimal two, BigDecimal than) {
        BigDecimal res = QSumUtil.sub(one, two);
        than = than == null ? BigDecimal.ZERO : than;
        if (res.compareTo(than) <= 0) return true;
        BigDecimal res2 = QSumUtil.sub(two, one);
        if (res2.compareTo(than) <= 0) return true;
        return false;
    }

    /**
    * 同步支付信息列表
    */
    public Order asyncPayment(Order order, List<FormOrderPayment> paymentList) {
        // 订单生成的金额
        BigDecimal price = order.getPrice_generate();
        // 支付的金额
        BigDecimal paid = BigDecimal.ZERO;
        // 累加
        for (FormOrderPayment op: paymentList) { paid = QSumUtil.add(paid, op.getPrice()); }
        // 对比
        if (!lessThanLimit(price, paid, VanLilyConfigure.MAX_PRICE_LIMIT))
            throw new QLogicException("支付金额总值应当与订单总金额相一致");
        // 加入支付
        order.setPayments(JSONUtil.toJsonStr(paymentList));
        // 状态
        order.setIs_paid(EnumBooleanTransfer.TRUE.v);
        // 时间
        order.setDate_paid(new Date());
        // 返回
        return order;
    }

    /**
    * 生成流水号
    */
    public String generateArticleNumber() {
        // 查询 3 天内的订单数据
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.gt(Order::getPublished, QDateUtil.beforeHour(QDateUtil.HOUR_24 * 3));
        List<Order> orderList = mapper.selectList(queryWrapper);
        // 检测空
        if (orderList == null) orderList = new ArrayList<>();
        // 流水号第一数字
        Integer first = orderList.size() + 100;
        // 流水号第二个数字
        Integer second = QValueUtil.randomInt(10);
        // 组合
        String result = first + "" + second;
        // 末尾值
        String suffix = "";
        // 查重复
        for (Order o: orderList) {
            // 若重复
            if (o.getArticle_number().equals(result)) {
                suffix = CHAR_A;
            }
        }
        // 返回
        return result + suffix;
    }

    /**
    * 同步訂單金額
    * 折扣定義：前端要把 discount 數值計算好，傳到後端，因為discount 是有區別的，有的 discount = 0.9，有的 discount = 5 元優惠券
    */
    public Order asyncOrderPrice(Order order, List<ViewOrderProduct> productList, List<FormOrderDiscount> discounts) {
        BigDecimal price = BigDecimal.ZERO;
        // 优先加入产品列表数据
        // 增加所有产品的价格
        for (ViewOrderProduct op: productList) price = QSumUtil.add(price, op.getPrice_newest_selling());

        // 验证 INDEX
        for (FormOrderDiscount od: discounts)
            if (od.getIndex() == null) throw new QLogicException("折扣的排序值不應該為空，否則無法正常計算訂單最終價格");
        // 再次過濾
        // 进行折扣的排序
        discounts = discounts.stream().filter(d -> QValueUtil.hasDecimal(d.getDiscount_price())).sorted(
                (d1, d2) -> (d1.getIndex() - d2.getIndex())
        ).collect(Collectors.toList());

        // 折扣總額
        BigDecimal price_discount = BigDecimal.ZERO;
        // 加入折扣进行计算
        for (FormOrderDiscount od: discounts)
            price_discount = QSumUtil.add(price_discount, od.getDiscount_price());

        // 設置訂單價格
        order.setPrice_generate(QSumUtil.sub(price, price_discount));
        // order.setPrice_paid();
        order.setPrice_discount(price_discount);
        order.setDiscounts(JSONUtil.toJsonStr(OrderTransfer.discountTransfer(discounts)));

        // 返回
        return order;
    }

    /**
    * 完善基本數據
    */
    public Order fillingBasicData(Order order, FormChargeSimply charge, List<FormOrderDiscount> discounts, List<FormOrderRemark> remarks) {
        // 訂單負責人
        if (charge != null) order.setCharge_checkout(JSONUtil.toJsonStr(charge));
        // 備註
        if (remarks != null && !remarks.isEmpty()) order.setRemarks(JSONUtil.toJsonStr(remarks));
        // 折扣
        if (discounts != null && !discounts.isEmpty()) order.setDiscounts(JSONUtil.toJsonStr(discounts));
        return order;
    }

    /**
    * 組裝訂單產品數據
    */
    public ViewOrderProduct groupOrderProduct(Long id, Short g, List<String> variations_article_number, List<FormFillingProductRequire> requires) {
        ViewOrderProduct one = new ViewOrderProduct();
        // 获取蛋糕原数据
        Cake cake = bridgeCakeService.one(id);

        // 檢測是否下架
        if (cake.getIs_allow().equals(EnumBooleanTransfer.FALSE.v)) throw new QLogicException("您選取的蛋糕已下架，您無法下單該蛋糕，蛋糕名稱: " + cake.getName());
        // 不檢測是否是限定蛋糕

        // 设置数据
        one.setId(id);
        one.setName(cake.getName());
        one.setHour_live(cake.getHour_live());
        one.setHour_fresh(cake.getHour_fresh());

        // 提取选择样式
        if (variations_article_number != null) {
            List<ViewCakeVariation> variations = bridgeCakeService.variationsByArticleNumber(cake, variations_article_number);
            one.setVariations(variations);
        }

        // 提取选择尺寸
        if (g != null) {
            ViewCakeSize size = bridgeCakeService.sizeByG(cake, g);
            one.setSize(size);

            // 提取价格
            one.setPrice_newest_selling(size.getPrice_newest_selling());
            one.setPrice_origin_selling(size.getPrice_origin_selling());
        }

        System.out.println("groupOrderProduct requires = " + requires );
        // 提取要求信息
        if (requires != null) {
            one.setRequires(OrderProductTransfer.transferRequire(requires));
        }
        System.out.println(one.getRequires());
        one.setIs_main_cake(EnumBooleanTransfer.TRUE.v);

        // 返回
        return one;
    }

    /**
     * 完善信息，转换下单产品去，数据库储存的产品
     */
    public List<ViewOrderProduct> fillingProductsToDataProducts(List<FormFillingOrderProduct> orderProductList) {
        if (orderProductList == null || orderProductList.isEmpty()) throw new QLogicException("客人未選取任何蛋糕，訂單完善信息失敗，請先讓客人選取蛋糕");
        List<ViewOrderProduct> result = new ArrayList<>();
        // 产品列表不允许为空
        for (FormFillingOrderProduct op: orderProductList) {
            if (op.getId() == null) throw new QLogicException("所選取的蛋糕數據為空，完善訂單信息失敗");
            if (op.getG() == null) throw new QLogicException("所選取的蛋糕克數異常，完善訂單信息失敗");
            // 執行轉換
            ViewOrderProduct one = groupOrderProduct(op.getId(), op.getG(), op.getVariations_article_number(), op.getRequires());
            // 添加
            result.add(one);
        }
        return result;
    }

    /**
    * 預約訂單，转换下单产品去，数据库储存的产品
    */
    public List<ViewOrderProduct> reserveProductsToDataProducts(List<FormReserveOrderProduct> orderProductList) {
        if (orderProductList == null) return null;
        List<ViewOrderProduct> result = new ArrayList<>();
        // 产品列表允许为空
        for (FormReserveOrderProduct op: orderProductList) {
            ViewOrderProduct one = groupOrderProduct(op.getId(), op.getG(), op.getVariations_article_number(), null);
            // 添加
            result.add(one);
        }
        return result;
    }
}
