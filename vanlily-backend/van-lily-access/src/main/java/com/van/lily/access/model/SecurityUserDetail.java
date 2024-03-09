package com.van.lily.access.model;

import com.van.lily.access.define.enums.EnumSecurityRoleType;
import com.van.lily.access.define.enums.EnumSecurityUserEntityType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

@Slf4j
@Data
@NoArgsConstructor
public class SecurityUserDetail implements UserDetails {
    private Long id;
    private String jwt;
    private String username; // maybe phone or email
    private String password;
    private EnumSecurityRoleType roleType;
    private EnumSecurityUserEntityType entityType;
    private Collection<? extends GrantedAuthority> permissions;

    /**
    * 登录时间
    */
    private Long loginTime;
    /**
    * 过期时间 默认 24 小时
    */
    private Long expireTime;

    // 默認 7 天
    final static Long DEF_EXPIRE_SECOND = 7 * 24 * 60 * 1000L;

    /**
    * 创建默认 登录用户
    */
    static SecurityUserDetail init(Long id, String username, String password) {
        SecurityUserDetail res = new SecurityUserDetail();
        res.expireTime = DEF_EXPIRE_SECOND;
        res.id = id;
        res.password = password;
        res.username = username;
        res.loginTime = System.currentTimeMillis();
        return res;
    }
    public static SecurityUserDetail initUser(Long id, String username, String password) {
        SecurityUserDetail res = init(id, username, password);
        res.entityType = EnumSecurityUserEntityType.user;
        return res;
    }
    public static SecurityUserDetail initCustomer(Long id, String phone, String password) {
        SecurityUserDetail res = init(id, phone, password);
        res.entityType = EnumSecurityUserEntityType.customer;
        return res;
    }

    public static SecurityUserDetail bad() { return new SecurityUserDetail(); }

    /**
    * 生成 权限角色
    */
    public static Collection<? extends GrantedAuthority> genAuthorities(EnumSecurityRoleType roleType) {
        return Collections.singletonList( new SimpleGrantedAuthority( roleType.getValue() ) );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (permissions == null) permissions =  genAuthorities(this.roleType);
        return permissions;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
