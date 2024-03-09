package com.van.lily.model.order.front.view.checklist;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@ApiModel(value = "检查清单视图項目")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewCheckListItem {
    @ApiModelProperty("排序值，重點標示值")
    private Short index;
    @ApiModelProperty("订单 ID")
    private Long order_id;
    @ApiModelProperty("产品 ID")
    private Long product_id;
    @ApiModelProperty("出货检查")
    private Short is_out_stock;
    @ApiModelProperty("完成检查")
    private Short is_complete;
    @ApiModelProperty("提交时间")
    private Date published;
    @ApiModelProperty("完成时间")
    private Date complete_time;
    @ApiModelProperty("清单文字信息")
    private String content;
}
