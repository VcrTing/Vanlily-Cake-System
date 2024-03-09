package com.van.lily.access.handler;

import cn.hutool.json.JSONUtil;
import com.van.lily.commons.core.AjaxResultUtil;
import com.van.lily.commons.utils.ServletUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class SecurityForbiddenHandier implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        String msg = "您沒有權限訪問該內容";
        ServletUtil.renderString(response, status, JSONUtil.toJsonStr(AjaxResultUtil.error(status, msg)));
    }
}