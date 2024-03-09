package com.van.lily.model.order.front.param.order;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.van.lily.commons.config.VanLilyConfigure;
import com.van.lily.commons.core.param.BasePageParam;
import com.van.lily.commons.define.enums.EnumBooleanTransfer;
import com.van.lily.commons.utils.define.QBetweenDate;
import com.van.lily.model.order.dao.order.DaoOrder;
import com.van.lily.model.order.define.enums.EnumOrderStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@ApiModel(value = "订单操作板过滤参数")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParamOrderOperaBoard extends BasePageParam {

    @ApiModelProperty("搜索")
    private String search;
    @ApiModelProperty("分页")
    private Short page;
    @ApiModelProperty("尺寸")
    private Short size;

    /**
    * 強制分頁
    */
    public ParamOrderOperaBoard resetPager() {
        page = 1;
        size = VanLilyConfigure.LIMIT_PAGE_OPERA_BOARD;
        return this;
    }

    /**
    * 查詢條件
    */
    public QueryWrapper<DaoOrder> queryWrapper() {
        QueryWrapper<DaoOrder> res = new QueryWrapper<>();

        // 加入时间，查询过去7天订单
        QBetweenDate bd = QBetweenDate.ofWhenDay(VanLilyConfigure.LIMIT_DAY_OPERA_BOARD, true);
        if (bd.has()) {
            res.gt(bd.getStarDate(), "me.published");
            res.lt(bd.getEndDate(), "me.published");
        }

        // 退单 与 工作 状态
        List<EnumOrderStatus> statusList = Arrays.asList(EnumOrderStatus.working, EnumOrderStatus.refunded);
        res.in("me.status_life", statusList);
        // 未完成
        res.ne("me.is_over", EnumBooleanTransfer.TRUE.v);

        // 前端要自己剔除 已付款的但是未退款的订单，因为这个订单应该展示在工作板
        // 已退款和已付款都是要展示的，但是已取消的订单无需展示

        // 返回
        return res;
    }
}
