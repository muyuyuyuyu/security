package com.woodfish.security.config;

import com.woodfish.security.authentication.config.SmsCodeAuthenticationSecurityConfig;
import com.woodfish.security.authentication.config.ValidateCodeAuthenticationSecurityConfig;
import com.woodfish.security.properties.SecurityProperties;
import com.woodfish.security.utils.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SpringSocialConfigurer;

@Configuration
@EnableResourceServer
public class AppResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private AuthenticationSuccessHandler woodfishAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler woodfishAuthenticationFailureHandler;

    // 验证码安全配置器
    @Autowired
    private ValidateCodeAuthenticationSecurityConfig validateCodeAuthenticationSecurityConfig;
    // 手机验证码的安全配置器
    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private SpringSocialConfigurer woodfishSocialConfigurer;

    /**
     * http Basic 实际上就是将我门的用户名和密码连接起来然后 使用base64进行加密，将加密后的密文放在http 的header 中进行验证
     * http.formLogin()
     * .loginPage("/woodfish-login.html")
     * .loginProcessingUrl("/authentication/form")  // 登录请求路径 action请求路径
     * .and() //表单的提交
     * .authorizeRequests()  // 认证的请求
     * .antMatchers("/woodfish-login.html").permitAll() // 放行这个链接
     * .anyRequest()// 任意的请求都拦截
     * .authenticated()
     * .and()
     * .csrf().disable();
     *
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage(SecurityConstants.DEFAULT_AUTHENTICATION_URL)
                .loginProcessingUrl(SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_FORM)
                .successHandler(woodfishAuthenticationSuccessHandler)
                .failureHandler(woodfishAuthenticationFailureHandler);
        http/*.apply(validateCodeAuthenticationSecurityConfig)
                .and()*/
                .apply(smsCodeAuthenticationSecurityConfig)
                .and()
                .authorizeRequests()
                .antMatchers(
                        SecurityConstants.DEFAULT_AUTHENTICATION_URL,
                        SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE,
                        SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*"
                ).permitAll().anyRequest()
                .authenticated()
                .and()
                .csrf().disable();
    }
}
