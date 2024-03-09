package com.van.lily.model.system.front.view.customer.jsonCustomer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewCustomerBirthday {

    private Date published;
    private Date birthday;
    private String remark;
    private Short is_self;
    private Short is_gregorian;
}
