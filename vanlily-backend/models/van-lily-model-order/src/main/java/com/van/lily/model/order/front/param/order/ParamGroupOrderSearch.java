package com.van.lily.model.order.front.param.order;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.van.lily.commons.config.VanLilyConfigure;
import com.van.lily.commons.core.param.BasePageParam;
import com.van.lily.commons.utils.qiong.QValueUtil;
import com.van.lily.model.order.dao.order.DaoOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "单个订单过滤组")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParamGroupOrderSearch extends BasePageParam {

    @ApiModelProperty("订单ID")
    private String id;
    @ApiModelProperty("订单内部编号")
    private String uuid;
    @ApiModelProperty("客人电话号码")
    private String phone;
    @ApiModelProperty("订单取货编号")
    private String article_number;

    @ApiModelProperty("查詢")
    private String search;

    @ApiModelProperty("分页")
    private Short page;
    @ApiModelProperty("尺寸")
    private Short size;

    /**
    * 強制分頁
    */
    public ParamGroupOrderSearch resetPager() {
        page = 1;
        size = VanLilyConfigure.LIMIT_PAGE_WORK_BOARD;
        return this;
    }

    /**
    * 查詢條件
    */
    public QueryWrapper<DaoOrder> queryWrapper() {
        QueryWrapper<DaoOrder> res = new QueryWrapper<>();

        // 加入搜索
        if (QValueUtil.hasLength(search)) {
            res.eq("me.id", search).or();
            res.like("me.uuid", search).or();
            res.like("me.article_number", search).or();
            return res;
        }

        if (QValueUtil.hasLength(id)) {
            res.eq("me.id", id);
            return res;
        }
        if (QValueUtil.hasLength(uuid)) {
            res.like("me.uuid", uuid);
            return res;
        }
        if (QValueUtil.hasLength(article_number)) {
            res.like("me.article_number", article_number);
            return res;
        }

        // 返回
        return res;
    }
}
