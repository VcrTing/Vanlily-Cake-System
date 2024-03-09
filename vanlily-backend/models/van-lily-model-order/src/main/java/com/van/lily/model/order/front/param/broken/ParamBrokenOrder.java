package com.van.lily.model.order.front.param.broken;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.van.lily.commons.core.param.BasePageParam;
import com.van.lily.commons.define.enums.EnumBooleanTransfer;
import com.van.lily.commons.utils.define.QBetweenDate;
import com.van.lily.commons.utils.qiong.QValueUtil;
import com.van.lily.model.order.define.enums.EnumOrderBrokenType;
import com.van.lily.model.order.entity.BrokenOrder;
import com.van.lily.model.order.entity.Delivery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@ApiModel(value = "坏单参数")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParamBrokenOrder extends BasePageParam {

    private String search;
    private String sort;
    private Short page;
    private Short size;

    @ApiModelProperty("坏单開始時間")
    private Date start_date;
    @ApiModelProperty("坏单結束時間")
    private Date end_date;

    @ApiModelProperty("所属 订单 是否已付款")
    private Short order_is_paid;
    @ApiModelProperty("意外类型")
    private EnumOrderBrokenType type_broken;

    /**
    * 坏单
    */
    public LambdaQueryWrapper<BrokenOrder> queryWrapper() {
        LambdaQueryWrapper<BrokenOrder> res = new LambdaQueryWrapper<>();
        res.orderBy(BasePageParam.hasSort(this.sort), BasePageParam.isAsc(this.sort), BrokenOrder::getId);
        // 加入时间
        QBetweenDate bd = QBetweenDate.init(start_date, end_date, false);
        if (bd.has()) {
            res.gt(BrokenOrder::getDate_broken, bd.getStarDate());
            res.lt(BrokenOrder::getDate_broken, bd.getEndDate());
        }
        // 加入搜索
        if (QValueUtil.hasLength(search)) {
            res.like(BrokenOrder::getRemark, search).or();
            res.like(BrokenOrder::getOrder_uuid, search).or();
            res.like(BrokenOrder::getParticipant, search).or();
        }
        // 其他
        if (QValueUtil.hasLength(type_broken))
            res.eq(BrokenOrder::getType_broken, type_broken).or();
        if (EnumBooleanTransfer.isNice(order_is_paid))
            res.eq(BrokenOrder::getOrder_is_paid, order_is_paid).or();
        // 返回
        return res;
    }
}
