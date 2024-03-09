package com.van.lily.access.conf;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConf extends WebSecurityConfigurerAdapter {

    /**
    * 白名单
    */
    static final String[] WHITE_LIST = new String[] {
            "/"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().and().csrf().disable();

        // http.authorizeRequests().antMatchers(STATIC_LINK).permitAll();
        http.authorizeRequests().antMatchers(WHITE_LIST).permitAll();

        http.authorizeRequests().anyRequest().authenticated();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // http.exceptionHandling().authenticationEntryPoint(authFailureHandier).accessDeniedHandler(forbiddenHandier);

        // 请求的 TOKEN
        // http.addFilterAt(securityAuthRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
