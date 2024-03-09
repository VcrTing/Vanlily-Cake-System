package com.van.lily.commons.core;

import org.springframework.http.HttpStatus;

/**
 * 统一的前端返回，生成工具
 *
 * @author QIONG
 */
public class AjaxResultUtil {

    final static String SUCCESS = "SUCCESS";

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public static AjaxResult success(Object data) {
        return new AjaxResult(HttpStatus.OK.value(), SUCCESS, data);
    }

    /**
     * 返回错误消息
     *
     * @return 默认 400 错误
     */
    public static AjaxResult error(String msg) {
        return new AjaxResult(HttpStatus.BAD_REQUEST.value(), msg);
    }

    /**
     * 返回错误消息
     *
     * @return 任意 CODE 错误
     */
    public static AjaxResult error(HttpStatus code, String msg) {
        return new AjaxResult(code.value(), msg);
    }
}
