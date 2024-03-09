package com.van.lily.module.order.bridge.impl;

import cn.hutool.json.JSONUtil;
import com.van.lily.commons.core.exception.QCloudException;
import com.van.lily.commons.keys.cache.KeyProductConstants;
import com.van.lily.commons.utils.qiong.QValueUtil;
import com.van.lily.framework.core.tools.RedisTool;
import com.van.lily.model.product.entity.Cake;
import com.van.lily.model.product.front.view.cake.jsonCake.ViewCakeSize;
import com.van.lily.model.product.front.view.cake.jsonCake.ViewCakeVariation;
import com.van.lily.model.product.front.view.cake.jsonCake.inner.ViewCakeVariationItem;
import com.van.lily.model.product.mapper.entity.CakeMapper;
import com.van.lily.module.order.bridge.BridgeCakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class BridgeCakeServiceImpl implements BridgeCakeService {

    @Autowired
    CakeMapper mapper;

    @Autowired
    RedisTool redisTool;

    /**
    * 根據 ID 獲取
    */
    public Cake one(Long id) {
        String key = KeyProductConstants.CAKE_BY_ID + id;
        Cake cake = redisTool.getObject(key);
        if (cake == null) {
            cake = mapper.selectById(id);
            if (cake != null) {
                saveToCache(cake);
                redisTool.expire(key);
            }
        } else { redisTool.expire(key); }
        if (cake == null) throw new QCloudException("蛋糕數據為空，請檢查該蛋糕是否存在。");
        return cake;
    }

    /**
    * 存入緩存
    */
    public void saveToCache(Cake cake) {
        if (cake != null && cake.getId() != null) {
            String key = KeyProductConstants.CAKE_BY_ID + cake.getId();
            redisTool.setObject(key, cake);
        }
    }

    /**
    * 獲取 尺寸
    */
    public List<ViewCakeSize> jsonSizes(String src) { return JSONUtil.toList(src, ViewCakeSize.class); }
    public String jsonSizes(List<ViewCakeSize> src) { return JSONUtil.toJsonStr(src); }
    public String jsonSize(ViewCakeSize src) { return JSONUtil.toJsonStr(src); }
    public ViewCakeSize jsonSize(String src) { return JSONUtil.toBean(src, ViewCakeSize.class); }
    public List<ViewCakeSize> getCakeSizes(Cake cake) {
        String src = cake.getSizes();
        if (QValueUtil.hasLength(src)) {
            List<ViewCakeSize> res = JSONUtil.toList(src, ViewCakeSize.class);
            if (res != null && !res.isEmpty()) return res;
        }
        throw new QCloudException("蛋糕尺寸數據為空，請檢查該蛋糕信息是否完善。");
    }
    /**
     * 獲取 樣式
     */
    public List<ViewCakeVariation> jsonVariations(String src) { return JSONUtil.toList(src, ViewCakeVariation.class); }
    public String jsonVariations(List<ViewCakeVariation> src) { return JSONUtil.toJsonStr(src); }
    public List<ViewCakeVariation> getCakeVariations(Cake cake) {
        String src = cake.getVariations();
        if (QValueUtil.hasLength(src)) {
            List<ViewCakeVariation> res = JSONUtil.toList(src, ViewCakeVariation.class);
            if (res != null && !res.isEmpty()) return res;
        }
        throw new QCloudException("蛋糕樣式數據為空，請檢查該蛋糕信息是否完善。");
    }

    /**
    * 尺寸 BY G
    */
    public ViewCakeSize sizeByG(Cake cake, Short g) {
        List<ViewCakeSize> sizes = getCakeSizes(cake);
        for (ViewCakeSize s: sizes) { if (s.getG().equals(g)) return s; }
        throw new QCloudException("無法定位蛋糕尺寸數據，請檢查該信息是否正確。");
    }

    /**
    * 样式 BY article_number
    */
    public List<ViewCakeVariation> variationsByArticleNumber(Cake cake, List<String> variations_article_number) {
        List<ViewCakeVariation> variations = getCakeVariations(cake);
        List<ViewCakeVariation> result = new ArrayList<>();
        // 循環所有樣式
        for (ViewCakeVariation v: variations ) {
            List<ViewCakeVariationItem> items = v.getItems();
            System.out.println("ViewCakeVariationItem = " + items);
            // 定位 ITEM
            ViewCakeVariationItem choice = null;
            for (ViewCakeVariationItem i: items) {
                String art_number = i.getArticle_number();
                // 定位選取的那個 ITEM
                if (variations_article_number.contains(art_number)) {
                    choice = i; break;
                }
            }
            // 定位完成
            if (choice == null) throw new QCloudException("無法定位蛋糕尺寸數據，請檢查該信息是否正確。");
            v.setItems(Collections.singletonList(choice));
            result.add(v);
        }
        return result;
    }

}
