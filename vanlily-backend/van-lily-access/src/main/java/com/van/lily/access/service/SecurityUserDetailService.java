package com.van.lily.access.service;

import com.van.lily.access.core.CacheEntityService;
import com.van.lily.access.model.SecurityUserDetail;
import com.van.lily.commons.core.exception.QAuthException;
import com.van.lily.model.system.entity.Customer;
import com.van.lily.model.system.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SecurityUserDetailService implements UserDetailsService {

    @Autowired
    CacheEntityService cacheEntityService;

    /**
    * 統一登錄時提取用戶的方法
    */
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 先從 USER 裡面獲取
        User u = cacheEntityService.getUser(username);
        if (u != null) {
            return SecurityUserDetail.initUser(u.getId(), u.getName(), u.getPassword());
        }
        // 再從 CUSTOMER 裡面獲取
        Customer c = cacheEntityService.getCustomer(username);
        if (c != null) {
            return SecurityUserDetail.initUser(c.getId(), c.getName(), c.getPassword());
        }
        // 未有找到
        throw new QAuthException("該用戶" + username + "還未註冊，請先註冊");
    }
}
