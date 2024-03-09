package com.van.lily.model.order.front.view.order.jsonOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewOrderRemark {

    private String type;
    private String type_text;

    private String content;
}
