package com.van.lily.module.product.transfer.impl;

import cn.hutool.json.JSONUtil;
import com.van.lily.commons.utils.qiong.QBeanUtil;
import com.van.lily.model.product.entity.Cake;
import com.van.lily.model.product.front.form.cake.FormCake;
import com.van.lily.module.product.transfer.TransferCakeService;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TransferCakeServiceImpl implements TransferCakeService {

    /**
    * FORM TO ENTITY
    */
    public Cake formToEntity(FormCake form) {
        Cake cake = QBeanUtil.convert(form, Cake.class);
        if (cake != null) {
            cake.setVariations(JSONUtil.toJsonStr(form.getVariations()));
            cake.setSizes(JSONUtil.toJsonStr(form.getSizes()));
            cake.setTags(JSONUtil.toJsonStr(form.getTags()));
            cake.setPublished(new Date());
        }
        return cake;
    }
}
