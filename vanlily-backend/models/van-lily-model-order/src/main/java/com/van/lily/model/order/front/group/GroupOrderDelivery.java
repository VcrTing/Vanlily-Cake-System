package com.van.lily.model.order.front.group;

import com.van.lily.model.order.entity.Delivery;
import com.van.lily.model.order.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupOrderDelivery {
    private Order order;
    private Delivery delivery;
    private Boolean canConnect;
}
