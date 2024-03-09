package com.van.lily.module.order.bridge;

import com.van.lily.model.product.entity.Cake;
import com.van.lily.model.product.front.view.cake.jsonCake.ViewCakeSize;
import com.van.lily.model.product.front.view.cake.jsonCake.ViewCakeVariation;
import com.van.lily.model.system.entity.Customer;

import java.util.List;

public interface BridgeCakeService {
    /**
    * 根据 ID 獲取 蛋糕信息
    */
    Cake one(Long id);

    /**
    * 存入緩存
    */
    void saveToCache(Cake cake);

    /**
    * 根据克数，获取所选尺寸
    */
    ViewCakeSize sizeByG(Cake cake, Short g);

    /**
     * 根据样式代号，获取所选样式
     */
    List<ViewCakeVariation> variationsByArticleNumber(Cake cake, List<String> variations_article_number);
}
