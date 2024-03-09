package com.van.lily.model.order.front.param.order;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.van.lily.commons.core.param.BasePageParam;
import com.van.lily.commons.define.enums.EnumBooleanTransfer;
import com.van.lily.commons.utils.define.QBetweenDate;
import com.van.lily.commons.utils.qiong.QValueUtil;
import com.van.lily.model.order.dao.order.DaoOrder;
import com.van.lily.model.order.define.enums.*;
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
public class ParamOrderForSystem extends BasePageParam {

    private String search;
    private String sort;
    private Short page;
    private Short size;

    @ApiModelProperty("开始日期")
    private Date start_date;
    @ApiModelProperty("结束日期")
    private Date end_date;

    @ApiModelProperty("订单来源记录")
    private EnumOrderFrom be_from;
    @ApiModelProperty("订单取货类型")
    private EnumOrderTakeType type_take;

    @ApiModelProperty("订单是否已完善")
    private Short is_fill;
    @ApiModelProperty("订单是否已支付")
    private Short is_paid;
    @ApiModelProperty("清單是否檢查完成")
    private Short is_check_complete;

    @ApiModelProperty("收银员")
    private Long cashier_id;

    @ApiModelProperty("全局生命状态")
    private EnumOrderStatus status_life;
    @ApiModelProperty("一些配送状态")
    private EnumOrderSendStatus[] status_sends;

    /**
    * 构建 查询表单
    */
    public QueryWrapper<DaoOrder> queryWrapper() {
        QueryWrapper<DaoOrder> res = new QueryWrapper<>();
        res.orderBy(BasePageParam.hasSort(this.sort), BasePageParam.isAsc(this.sort),"me.id");

        // 加入时间
        QBetweenDate bd = QBetweenDate.init(start_date, end_date, false);
        if (bd.has()) {
            res.gt(bd.getStarDate(), "me.published");
            res.lt(bd.getEndDate(), "me.published");
        }
        // 加入搜索
        if (QValueUtil.hasLength(search)) {
            res.like("me.uuid", search).or();
            res.like("me.be_from", search).or();
            res.like("me.type_take", search).or();
            res.like("me.article_number", search).or();
        }
        // 基本
        if (QValueUtil.hasLength(cashier_id))
            res.eq("me.cashier_id", cashier_id).or();
        if (QValueUtil.hasLength(be_from))
            res.eq("me.be_from", be_from).or();
        if (QValueUtil.hasLength(type_take))
            res.eq("me.type_take", type_take).or();
        // 状态一
        if (EnumBooleanTransfer.isNice(is_fill))
            res.eq("me.is_fill", is_fill).or();
        if (EnumBooleanTransfer.isNice(is_paid))
            res.eq("me.is_paid", is_paid).or();
        if (EnumBooleanTransfer.isNice(is_check_complete))
            res.eq("me.is_check_complete", is_check_complete).or();
        // 状态二
        if (QValueUtil.hasLength(status_life))
            res.eq("me.status_life", status_life).or();
        if (QValueUtil.hasLength(status_life))
            res.in("me.status_send", (Object) status_sends).or();

        // 返回
        return res;
    }
}
