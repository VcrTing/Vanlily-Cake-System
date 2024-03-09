package com.van.lily.commons.core.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class QCloudException extends RuntimeException {
    private static final long serialVersionUID = 2L;

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误提示
     */
    private String message;

    /**
     * 空构造方法，避免反序列化问题
     */

    public QCloudException(String message) {
        this.message = message;
        this.code = HttpStatus.BAD_REQUEST.value();
    }

    public QCloudException(String message, HttpStatus code) {
        this.message = message;
        this.code = code.value();
    }
}