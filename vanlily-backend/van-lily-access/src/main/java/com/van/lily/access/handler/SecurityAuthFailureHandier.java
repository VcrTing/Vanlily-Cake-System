package com.van.lily.access.handler;

import cn.hutool.json.JSONUtil;
import com.van.lily.commons.core.AjaxResultUtil;
import com.van.lily.commons.utils.ServletUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SecurityAuthFailureHandier implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        String msg = "認證 TOKEN 失敗，請登錄。";
        ServletUtil.renderString(response, status, JSONUtil.toJsonStr(AjaxResultUtil.error(status, msg)));
    }
}