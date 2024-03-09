package com.van.lily.model.order.front.param.delivery;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.van.lily.commons.core.param.BasePageParam;
import com.van.lily.commons.define.enums.EnumBooleanTransfer;
import com.van.lily.commons.utils.define.QBetweenDate;
import com.van.lily.commons.utils.qiong.QValueUtil;
import com.van.lily.model.order.dao.order.DaoOrder;
import com.van.lily.model.order.entity.Delivery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@ApiModel(value = "配送过滤参数")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParamDelivery extends BasePageParam {

    private String search;
    private String sort;
    private Short page;
    private Short size;

    @ApiModelProperty("配送數據開始時間")
    private Date start_date;
    @ApiModelProperty("配送數據結束時間")
    private Date end_date;

    @ApiModelProperty("是否在配送")
    private Short is_in_delivery;
    @ApiModelProperty("是否送達")
    private Short is_arrive;
    @ApiModelProperty("是否结束")
    private Short is_over;
    @ApiModelProperty("是否延迟送货")
    private Short is_delay;


    public LambdaQueryWrapper<Delivery> queryWrapper() {
        LambdaQueryWrapper<Delivery> res = new LambdaQueryWrapper<>();
        res.orderBy(BasePageParam.hasSort(this.sort), BasePageParam.isAsc(this.sort), Delivery::getId);

        // 加入时间
        QBetweenDate bd = QBetweenDate.init(start_date, end_date, false);
        if (bd.has()) {
            res.gt(Delivery::getPublished, bd.getStarDate());
            res.lt(Delivery::getPublished, bd.getEndDate());
        }
        // 加入搜索
        if (QValueUtil.hasLength(search)) {
            res.like(Delivery::getType_take, search).or();
            res.like(Delivery::getReceive_name, search).or();
            res.like(Delivery::getReceive_phone, search).or();
            res.like(Delivery::getReceive_content_1, search).or();
        }
        // 狀態
        if (EnumBooleanTransfer.isNice(is_over))
            res.eq(Delivery::getIs_over, is_over).or();
        if (EnumBooleanTransfer.isNice(is_delay))
            res.eq(Delivery::getIs_delay, is_delay).or();
        if (EnumBooleanTransfer.isNice(is_arrive))
            res.eq(Delivery::getIs_arrive, is_arrive).or();
        if (EnumBooleanTransfer.isNice(is_in_delivery))
            res.eq(Delivery::getIs_in_delivery, is_in_delivery).or();

        // 返回
        return res;
    }
    /**
    * 构建 查询表单
    * /
    public QueryWrapper<Delivery> queryWrapper() {
        QueryWrapper<Delivery> res = new QueryWrapper<>();
        res.orderBy(BasePageParam.hasSort(this.sort), BasePageParam.isAsc(this.sort), "me.id");

        // 加入时间
        QBetweenDate bd = QBetweenDate.init(start_date, end_date, false);
        if (bd.has()) {
            res.gt(bd.getStarDate(), "me.published");
            res.lt(bd.getEndDate(), "me.published");
        }
        // 加入搜索
        if (QValueUtil.hasLength(search)) {
            res.like("me.type_take", search).or();
            res.like("me.receive_name", search).or();
            res.like("me.receive_phone", search).or();
            res.like("me.receive_content_1", search).or();
        }
        // 狀態
        if (EnumBooleanTransfer.isNice(is_over))
            res.eq("me.is_over", is_over).or();
        if (EnumBooleanTransfer.isNice(is_delay))
            res.eq("me.is_delay", is_delay).or();
        if (EnumBooleanTransfer.isNice(is_arrive))
            res.eq("me.is_arrive", is_arrive).or();
        if (EnumBooleanTransfer.isNice(is_in_delivery))
            res.eq("me.is_in_delivery", is_in_delivery).or();

        // 返回
        return res;
    }
     */
}
