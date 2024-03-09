package com.van.lily.model.order.transfer.view;

import cn.hutool.json.JSONUtil;
import com.van.lily.commons.utils.qiong.QBeanUtil;
import com.van.lily.commons.utils.qiong.QValueUtil;
import com.van.lily.model.order.entity.BrokenOrder;
import com.van.lily.model.order.front.view.broken.ViewBrokenOrder;
import com.van.lily.model.order.front.view.broken.jsonInner.ViewOrderBrokenContent;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public final class ViewBrokenTransfer {

    public static ViewBrokenOrder transfer(BrokenOrder src) {
        ViewBrokenOrder view = QBeanUtil.convert(src, ViewBrokenOrder.class);
        if (view == null) {
            log.error("轉換異常: BROKEN ORDER TO VIEW ERROR，Method: ViewBrokenTransfer.transfer");
            return null;
        }
        view.setContent(JSONUtil.toBean(QValueUtil.mustJsonBean(src.getContent()), ViewOrderBrokenContent.class));
        return view;
    }

    public static List<ViewBrokenOrder> transfers(List <BrokenOrder> src) {
        return src.stream().map(ViewBrokenTransfer::transfer).filter(Objects::nonNull).collect(Collectors.toList());
    }
}
