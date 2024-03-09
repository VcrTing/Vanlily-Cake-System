package com.van.lily.commons.utils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.van.lily.commons.core.AjaxResult;
import com.van.lily.commons.core.AjaxResultUtil;
import com.van.lily.commons.core.param.BasePageParam;

import java.util.List;

public class PageHelperUtil {

    final static short PAGE = 1;
    final static short SIZE = 25;
    final static short MIN_SIZE = 10;

    /**
    * 数据量在最小分页数量内
    */
    public static <T> boolean isNotInMinSize(List<T> list) {
        return list != null && list.size() >= MIN_SIZE;
    }

    public static Short mustPage(BasePageParam param) {
        Short res = PAGE;
        if (param != null) res = param.getPage();
        return res == null ? PAGE : res;
    }
    public static Short mustSize(BasePageParam param) {
        Short res = SIZE;
        if (param != null) res = param.getSize();
        return res == null ? SIZE : res;
    }

    /**
    * 开始分页
    */
    public static void start(BasePageParam param) {
        PageHelper.startPage(mustPage(param), mustSize(param));
    }

    /**
    * 返回结果
    */
    public static <T> AjaxResult successResult(List<T> lists, Short size) {
        PageInfo<T> result = new PageInfo<>(lists);
        result.setPageSize(size == null ? SIZE : size);
        return AjaxResultUtil.success(result);
    }
    public static <T> AjaxResult successResult(List<T> lists, BasePageParam param) {
        return successResult(lists, mustSize(param));
    }
}
