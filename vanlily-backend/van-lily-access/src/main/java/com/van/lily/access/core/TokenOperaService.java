package com.van.lily.access.core;

import com.van.lily.access.define.CacheConstants;
import com.van.lily.access.model.SecurityUserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
public class TokenOperaService {
    // 小于 60分钟 就 刷新 TOKEN
    private static final Long EXPIRE_TIME_MINUTE_MIN = 60L;
    // 令牌自定义标识
    private static final String HEADER = "Authorization";
    // 令牌前缀
    public static final String TOKEN_PREFIX = "Bearer ";

    @Autowired
    TokenCacheService cacheService;
    @Autowired
    TokenJwtService jwtService;

    /**
    * 获取用户身份信息
    *
    * @return 用户信息
    */
    public SecurityUserDetail getUserFromJwt(HttpServletRequest request) {
        String token = getTokenFromRequest(request);
        return (StringUtils.hasLength(token)) ? cacheService.get(jwtService.decode(token)) : null;
    }

    /**
    * 验证令牌有效期，相差不足20分钟，自动刷新缓存
    *
    * @param authUser 登录信息
    * @return 令牌
    */
    public void continueToken(SecurityUserDetail authUser) {
        Long expireTime = authUser.getExpireTime();
        if (expireTime != null) {
            if (expireTime - System.currentTimeMillis() <= EXPIRE_TIME_MINUTE_MIN) { refreshToken(authUser); } }
    }

    /**
    * 刷新令牌有效期
    *
    * @param src 登录用戶信息
    */
    public void refreshToken(SecurityUserDetail src) {
        log.debug("刷新 TOKEN");
        src.setLoginTime(System.currentTimeMillis());
        src.setExpireTime(src.getLoginTime() + CacheConstants.AUTH_EXPIRE_MINUET);
        cacheService.save(src);
    }

    /**
    * 获取请求 TOKEN
    *
    * @param request
    * @return token
    */
    private String getTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader(HEADER);
        if (StringUtils.hasLength(token) && token.startsWith(TOKEN_PREFIX)) {
            return token.replace(TOKEN_PREFIX, "");
        }
        return token;
    }

}
