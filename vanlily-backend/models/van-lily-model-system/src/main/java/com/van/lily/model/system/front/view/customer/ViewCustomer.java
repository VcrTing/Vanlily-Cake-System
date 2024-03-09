package com.van.lily.model.system.front.view.customer;

import com.van.lily.commons.core.annotations.TableJsonField;
import com.van.lily.model.system.define.enums.EnumCustomerGender;
import com.van.lily.model.system.front.view.customer.jsonCustomer.ViewCustomerBeFrom;
import com.van.lily.model.system.front.view.customer.jsonCustomer.ViewCustomerBirthday;
import com.van.lily.model.system.front.view.customer.jsonCustomer.ViewCustomerTag;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewCustomer {

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
    @ApiModelProperty("个人描述")
    private String description;
    @ApiModelProperty("性别")
    private EnumCustomerGender gender;

    @ApiModelProperty("客户内部编号")
    private String uuid;
    @ApiModelProperty("是否热门客户")
    private Short is_hot;
    @ApiModelProperty("是否劣质客户")
    private Short is_inferior;

    @ApiModelProperty("客户标签")
    @TableJsonField
    private List<ViewCustomerTag> tags;
    @ApiModelProperty("来源类型")
    @TableJsonField
    private List<ViewCustomerBeFrom> be_froms;
    @ApiModelProperty("生日记录")
    @TableJsonField
    private List<ViewCustomerBirthday> birthdays;
}
