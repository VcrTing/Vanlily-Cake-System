package com.van.lily.model.order.front.view.broken.jsonInner;

import com.van.lily.model.order.define.enums.EnumPersonBelongType;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "坏单参与者")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewOrderBrokenParticipant {

    private Long id;
    private String name;
    private String phone;
    private EnumPersonBelongType typed;
}
