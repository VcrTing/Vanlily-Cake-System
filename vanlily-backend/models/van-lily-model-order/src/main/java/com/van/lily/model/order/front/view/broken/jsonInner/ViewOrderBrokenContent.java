package com.van.lily.model.order.front.view.broken.jsonInner;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@ApiModel(value = "坏单内容")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewOrderBrokenContent {

    private String content;

}
