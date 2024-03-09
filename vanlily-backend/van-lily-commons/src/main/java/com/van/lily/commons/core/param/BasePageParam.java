package com.van.lily.commons.core.param;


import com.van.lily.commons.define.web.WebConstants;
import com.van.lily.commons.utils.qiong.QValueUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.management.QueryEval;
import java.io.Serializable;

@Data
public class BasePageParam implements Serializable {

    @ApiModelProperty("搜寻数据")
    private String search;
    @ApiModelProperty("排序")
    private String sort;
    @ApiModelProperty("分页")
    private Short page;
    @ApiModelProperty("尺寸")
    private Short size;

    public Boolean hasSort() {
        return QValueUtil.hasLength(sort);
    }
    public static Boolean hasSort(String src) {
        return QValueUtil.hasLength(src);
    }
    public Boolean isAsc() {
        return hasSort() && QValueUtil.hasText(sort, WebConstants.SORT_ASC);
    }
    public static Boolean isAsc(String src) {
        return hasSort(src) && QValueUtil.hasText(src, WebConstants.SORT_ASC);
    }
    public Boolean hasSearch() {
        return QValueUtil.hasLength(search);
    }

}
