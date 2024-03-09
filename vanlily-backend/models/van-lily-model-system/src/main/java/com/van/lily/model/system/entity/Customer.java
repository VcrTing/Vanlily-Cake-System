package com.van.lily.model.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.van.lily.commons.core.annotations.TableJsonField;
import com.van.lily.commons.define.data.SqlFieldConstants;
import com.van.lily.model.system.define.SystemTableNameDefine;
import com.van.lily.model.system.define.enums.EnumCustomerGender;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@ApiModel(value = "客人")
@TableName(SystemTableNameDefine.CUSTOMER)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @TableLogic(value = SqlFieldConstants.LIVE, delval = SqlFieldConstants.DIE)
    private Short live;
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private Date published;

    @ApiModelProperty("电邮，登录第二凭证")
    private String email;
    @ApiModelProperty("电话，登录第一凭证")
    private String phone;
    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("昵称")
    private String nickname;
    @ApiModelProperty("头像 LINK")
    @TableJsonField
    private String face;
    @ApiModelProperty("真实姓名")
    private String name;
    @ApiModelProperty("性别")
    private EnumCustomerGender gender;
    @ApiModelProperty("个人描述")
    private String description;

    @ApiModelProperty("客户内部编号")
    private String uuid;
    @ApiModelProperty("是否热门客户")
    private Short is_hot;
    @ApiModelProperty("是否劣质客户")
    private Short is_inferior;

    @ApiModelProperty("客户标签")
    @TableJsonField
    private String tags;
    @ApiModelProperty("生日记录")
    @TableJsonField
    private String birthdays;
    @ApiModelProperty("来源类型")
    @TableJsonField
    private String be_froms;
}
