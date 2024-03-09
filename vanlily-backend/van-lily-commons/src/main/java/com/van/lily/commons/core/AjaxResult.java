package com.van.lily.commons.core;

import java.util.HashMap;

/**
 * 统一的前端返回
 * code 统一使用 int 类型，
 * 文字信息统一放到 msg 里面，
 * data 只允许用来装 List/Map/Bean
 *
 * @author QIONG
 */
public class AjaxResult extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    public static final String CODE_TAG = "code";
    /**
     * 返回内容
     */
    public static final String MSG_TAG = "msg";
    /**
     * 数据对象
     */
    public static final String DATA_TAG = "data";

    /*
     * 扩展数据
     * public static final String EXTRA_TAG = "extra";
     */

    /**
    * 构造函数
    */
    public AjaxResult(int code, String msg, Object data) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        super.put(DATA_TAG, data);
    }
    /**
     * 构造函数
     */
    public AjaxResult(int code, String msg) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        super.put(DATA_TAG, null);
    }
}
