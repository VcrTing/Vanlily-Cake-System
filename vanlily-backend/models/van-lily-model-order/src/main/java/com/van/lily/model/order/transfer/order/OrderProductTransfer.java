package com.van.lily.model.order.transfer.order;

import com.van.lily.commons.core.exception.QLogicException;
import com.van.lily.commons.define.enums.EnumBooleanTransfer;
import com.van.lily.commons.utils.qiong.QBeanUtil;
import com.van.lily.commons.utils.qiong.QListUtil;
import com.van.lily.model.order.front.form.filling.jsonProduct.inner.FormFillingProductRequire;
import com.van.lily.model.order.front.view.order.jsonOrder.ViewOrderProduct;
import com.van.lily.model.order.front.view.order.jsonOrder.jsonProduct.ViewOrderProductRequire;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public final class OrderProductTransfer {
    /**
    * 转换
    */
    public static List<ViewOrderProductRequire> transferRequire(List<FormFillingProductRequire> requireList) {
        return requireList.stream().map(r -> {
            ViewOrderProductRequire require = QBeanUtil.convert(r, ViewOrderProductRequire.class);
            if (require == null) throw new QLogicException("蛋糕需求數據格式異常，無法轉換。");
            require.setPublished(new Date());
            return require;
        }).collect(Collectors.toList());
    }

    /**
    * 取出主蛋糕产品
    */
    public static List<ViewOrderProduct> filterMainCakeList(List<ViewOrderProduct> productList) {
        return QListUtil.isGoodList(productList) ?
                productList.stream().filter(p->EnumBooleanTransfer.isTrue(p.getIs_main_cake()))
                        .collect(Collectors.toList()) :
                new ArrayList<>();
    }
}
