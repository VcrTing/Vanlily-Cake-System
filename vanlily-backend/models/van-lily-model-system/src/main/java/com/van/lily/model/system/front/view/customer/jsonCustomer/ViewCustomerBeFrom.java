package com.van.lily.model.system.front.view.customer.jsonCustomer;

import com.van.lily.model.system.define.enums.EnumCustomerFrom;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewCustomerBeFrom {

    private Date published;
    private String remark;
    private EnumCustomerFrom be_from;
}
