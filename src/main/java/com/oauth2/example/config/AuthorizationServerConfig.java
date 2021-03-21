package com.oauth2.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

import javax.annotation.Resource;


/**
 * 授权服务器配置
 * @author zhoufeng
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private UserDetailsService userDetailsService;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                // 客户端id
                .withClient("my-client-1")
                // 客户端密码
                .secret(new BCryptPasswordEncoder().encode("12345678"))
                // 支持授权模式
                .authorizedGrantTypes("authorization_code", "implicit", "client_credentials", "password", "refresh_token")
                // 授权范围
                .scopes("getUserName", "getUserOpenId")
                // 回调地址
                .redirectUris("http://www.baidu.com")
                // accessToken的有效期只有90秒
                .accessTokenValiditySeconds(90)
                // refreshToken的有效期只有90秒
                .refreshTokenValiditySeconds(90);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // super.configure(security);
        // 开启/oauth/token_key 验证端口无权限访问 jwtToken使用
        security.tokenKeyAccess("permitAll()");
        // 开启/oauth/check_token 验证端口认证权限访问
        security.checkTokenAccess("isAuthenticated()");
        // 让/oauth/token 支持client_id以及client_secret 作登录认证
        security.allowFormAuthenticationForClients();
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // 设置令牌存于redis 默认存在内存
        // endpoints.tokenStore(tokenStore());
        // 支持password模式要配置AuthenticationManager
        endpoints.authenticationManager(authenticationManager);
        // 要使用refresh_token的话，需要额外配置userDetailsService
        endpoints.userDetailsService(userDetailsService);
        endpoints.reuseRefreshTokens(false);
        // 自定义端点
        // endpoints.pathMapping(defaultPath, customPath)
    }


    public static void main(String[] args) {
        System.out.println(java.util.Base64.getEncoder().encodeToString("my-client-1:12345678".getBytes()));
    }
}