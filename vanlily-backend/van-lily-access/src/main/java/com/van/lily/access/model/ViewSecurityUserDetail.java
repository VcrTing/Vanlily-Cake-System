package com.van.lily.access.model;

import com.van.lily.access.define.CacheConstants;
import com.van.lily.access.define.enums.EnumSecurityRoleType;
import com.van.lily.access.define.enums.EnumSecurityUserEntityType;
import com.van.lily.commons.utils.qiong.QBeanUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

@Slf4j
@Data
@NoArgsConstructor
public class ViewSecurityUserDetail implements Serializable {

    private Long id;
    private String username; // maybe phone or email
    private EnumSecurityUserEntityType entityType;

    /**
    * 登录时间
    */
    private Long loginTime;
    /**
    * 过期时间 默认 24 小时
    */
    private Long expireTime;

    public static ViewSecurityUserDetail init(Long id, String typeString) {
        ViewSecurityUserDetail view = new ViewSecurityUserDetail();
        view.setId(id);
        view.setEntityType(EnumSecurityUserEntityType.fromString(typeString));
        view.setLoginTime(System.currentTimeMillis());
        return view;
    }

    public static SecurityUserDetail init(ViewSecurityUserDetail view) {
        return QBeanUtil.convert(view, SecurityUserDetail.class);
    }
    public static ViewSecurityUserDetail init(SecurityUserDetail src) {
        return QBeanUtil.convert(src, ViewSecurityUserDetail.class);
    }
}
