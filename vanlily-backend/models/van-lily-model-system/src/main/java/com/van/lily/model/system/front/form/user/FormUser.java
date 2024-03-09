package com.van.lily.model.system.front.form.user;

import com.van.lily.commons.core.annotations.TableJsonField;
import com.van.lily.model.system.define.enums.EnumCustomerGender;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "新增用戶")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormUser {
    @ApiModelProperty("員工編號")
    private String number;

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
