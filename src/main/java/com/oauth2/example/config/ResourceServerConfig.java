package com.oauth2.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * 资源服务器配置
 * @author zhoufeng
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        super.configure(resources);
    }

    /**
     * 用于配置对受保护的资源的访问规则
     * 默认情况下所有不在/oauth/**下的资源都是受保护的资源
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 配置特定路径 会触发该HttpSecurity
        http.requestMatchers().antMatchers("/oauth2/**");
        // 基于HttpServletRequest进行访问限制
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET,"/oauth2/user-name").access("#oauth2.hasScope('getUserName')")
                .antMatchers(HttpMethod.GET,"/oauth2/user-open-id").access("#oauth2.hasScope('getUserOpenId')")
                // 任何请求都需要认证
                .anyRequest().authenticated()
                // 取消跨域防护
                .and().csrf().disable();

    }
}