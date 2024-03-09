package com.van.lily.model.order.entity.remind;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.van.lily.commons.core.annotations.TableJsonField;
import com.van.lily.commons.define.data.SqlFieldConstants;
import com.van.lily.model.order.define.OrderTableNameDefine;
import com.van.lily.model.order.define.enums.EnumRemindOrderType;
import com.van.lily.model.order.define.enums.EnumRemindToType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@ApiModel(value = "订单提醒")
@TableName(OrderTableNameDefine.ORDER_REMIND)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRemind {

    @TableLogic(value = SqlFieldConstants.LIVE, delval = SqlFieldConstants.DIE)
    private Short live;
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private Date published;

    @ApiModelProperty("发出提醒的运作时间")
    private Date remind_time;
    @ApiModelProperty("实际运作时间")
    private Date date_work;

    @ApiModelProperty("是否成功完成")
    private Short is_success;
    @ApiModelProperty("是否暂停")
    private Short is_stop;
    @ApiModelProperty("暂停时的时间")
    private Date date_stop;
    @ApiModelProperty("是否结束")
    private Short is_over;
    @ApiModelProperty("结束时间")
    private Date date_over;

    @ApiModelProperty("接收者")
    @TableJsonField
    private String remind_to;
    @ApiModelProperty("承载数据")
    @TableJsonField
    private String remind_data;

    @ApiModelProperty("该提醒的接收类型")
    private EnumRemindToType type_to;
    @ApiModelProperty("该提醒的工作类型")
    private EnumRemindOrderType type_work;
}
