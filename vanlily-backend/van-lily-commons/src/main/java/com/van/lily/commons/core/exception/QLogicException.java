package com.van.lily.commons.core.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class QLogicException extends RuntimeException {
    private static final long serialVersionUID = 1L;

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

    public QLogicException(String message) {
        this.message = message;
        this.code = HttpStatus.BAD_REQUEST.value();
    }

    public QLogicException(String message, HttpStatus code) {
        this.message = message;
        this.code = code.value();
    }
}