package com.van.lily.model.order.front.param.order;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.van.lily.commons.config.VanLilyConfigure;
import com.van.lily.commons.core.param.BasePageParam;
import com.van.lily.commons.define.enums.EnumBooleanTransfer;
import com.van.lily.commons.utils.define.QBetweenDate;
import com.van.lily.model.order.dao.order.DaoOrder;
import com.van.lily.model.order.define.enums.EnumOrderMakeStatus;
import com.van.lily.model.order.define.enums.EnumOrderSendStatus;
import com.van.lily.model.order.define.enums.EnumOrderStatus;
import com.van.lily.model.order.define.enums.EnumOrderTakeType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@ApiModel(value = "订单过滤参数")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParamOrderWorkBoard extends BasePageParam {

    @ApiModelProperty("开始日期")
    private Date start_date;
    @ApiModelProperty("结束日期")
    private Date end_date;

    @ApiModelProperty("分页")
    private Short page;
    @ApiModelProperty("尺寸")
    private Short size;

    /**
    * 強制分頁
    */
    public ParamOrderWorkBoard resetPager() {
        page = 1;
        size = VanLilyConfigure.LIMIT_PAGE_WORK_BOARD;
        return this;
    }

    /**
    * 查詢條件
    */
    public QueryWrapper<DaoOrder> queryWrapper() {
        QueryWrapper<DaoOrder> res = new QueryWrapper<>();

        // 加入时间
        QBetweenDate bd = QBetweenDate.init(start_date, end_date, false);
        if (bd.has()) {
            res.gt(bd.getStarDate(), "me.published");
            res.lt(bd.getEndDate(), "me.published");
        }

        // 加入工作板限制
        res.eq("me.status_life", EnumOrderStatus.working);
        res.eq("me.is_fill", EnumBooleanTransfer.TRUE.v);
        res.eq("me.is_paid", EnumBooleanTransfer.TRUE.v);
        res.eq("me.is_over", EnumBooleanTransfer.FALSE.v);

        // 返回
        return res;
    }
}
