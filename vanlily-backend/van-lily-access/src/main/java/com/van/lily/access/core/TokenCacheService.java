package com.van.lily.access.core;

import com.van.lily.access.define.CacheConstants;
import com.van.lily.access.define.enums.EnumSecurityUserEntityType;
import com.van.lily.access.model.SecurityUserDetail;
import com.van.lily.access.model.ViewSecurityUserDetail;
import com.van.lily.framework.core.tools.RedisTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TokenCacheService {

    @Autowired
    RedisTool redisTool;

    String key(Long id, EnumSecurityUserEntityType entityType) {
        return CacheConstants.KEY_AUTH_PREFIX + entityType.getValue() + ":id:" + id;
    }

    /**
    * 存入
    */
    public void save(SecurityUserDetail userDetail) {
        ViewSecurityUserDetail view = ViewSecurityUserDetail.init(userDetail);
        redisTool.setObject(key(view.getId(), view.getEntityType()), view, CacheConstants.AUTH_EXPIRE_MINUET);
    }

    /**
    * 取出
    */
    public SecurityUserDetail get(Long id, EnumSecurityUserEntityType entityType) {
        log.debug("登录用户的 KEY = `{}`", key(id, entityType));
        ViewSecurityUserDetail view = redisTool.getObject(key(id, entityType));
        return ViewSecurityUserDetail.init(view);
    }
    public SecurityUserDetail get(ViewSecurityUserDetail src) {
        if (src == null) return null;
        log.debug("登录用户的 KEY = `{}`", key(src.getId(), src.getEntityType()));
        ViewSecurityUserDetail view = redisTool.getObject(key(src.getId(), src.getEntityType()));
        return ViewSecurityUserDetail.init(view);
    }
}
