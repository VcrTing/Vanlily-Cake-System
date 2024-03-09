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

@ApiModel(value = "员工")
@TableName(SystemTableNameDefine.USER)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

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
    @ApiModelProperty("头像 LINK")
    @TableJsonField
    private String face;
    @ApiModelProperty("真实姓名")
    private String name;
    @ApiModelProperty("性别")
    private EnumCustomerGender gender;


}
