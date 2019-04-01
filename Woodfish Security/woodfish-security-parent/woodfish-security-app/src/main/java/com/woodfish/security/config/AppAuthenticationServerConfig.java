package com.woodfish.security.config;

import com.woodfish.security.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * 加上这些 认证控制器
 * "{[/oauth/authorize]}"
 * "{[/oauth/authorize],methods=[POST],
 * "{[/oauth/token],methods=[GET]}"
 * "{[/oauth/token],methods=[POST]}"
 * "{[/oauth/check_token]}"
 * "{[/oauth/confirm_access]}"
 * "{[/oauth/error]}"
 */
@Slf4j
@Configuration
@EnableAuthorizationServer
public class AppAuthenticationServerConfig {

/*    // 认证管理器

    private final AuthenticationManager authenticationManager;

    @Autowired
    private SecurityProperties securityProperties;
    @Autowired(required = false)
    private TokenStore tokenStore;

    @Qualifier(value = "woodFishUserDetailService")
    @Autowired
    private UserDetailsService woodfishUserDetailsService;

    public AppAuthenticationServerConfig(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        log.info("你来了吗？？？？？？？？111111111111111");
        endpoints.authenticationManager(authenticationManager).userDetailsService(woodfishUserDetailsService);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        log.info("你来了吗？？？？？？？？222222222222222222222");
        //  内存内部存储的我们的客户端
        clients.inMemory()
                .withClient("woodfish")
                .secret("woodfish")
                .redirectUris("http://example.com", "http://ora.com")
                .and()
                .withClient("woodfish1")
                .secret("woodfish1")
                .redirectUris("http://example.com")
                .authorizedGrantTypes("refresh_token", "password")
                .accessTokenValiditySeconds(7200)
                .scopes("all", "read", "write");
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 这里使用什么密码需要 根据上面配置client信息里面的密码类型决定
        // 目前上面配置的是无加密的密码
        security.passwordEncoder(new BCryptPasswordEncoder());
    }*/

}
