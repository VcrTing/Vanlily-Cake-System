package com.van.lily.model.system.front.view.customer.jsonCustomer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewCustomerTag {

    private Date published;
    private String name;
    private String remark;
}
