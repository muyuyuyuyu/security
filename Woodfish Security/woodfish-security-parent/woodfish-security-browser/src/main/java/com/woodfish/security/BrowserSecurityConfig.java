package com.woodfish.security;

import com.woodfish.security.authentication.config.SmsCodeAuthenticationSecurityConfig;
import com.woodfish.security.authentication.config.ValidateCodeAuthenticationSecurityConfig;
import com.woodfish.security.properties.SecurityProperties;
import com.woodfish.security.utils.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SpringSocialConfigurer;

@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private FormSecurityConfig formSecurityConfig;
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
    protected void configure(HttpSecurity http) throws Exception {
        formSecurityConfig.applyPasswordLoginAuthenticationConfig(http);
        http.
                apply(validateCodeAuthenticationSecurityConfig)
                    .and()
                .apply(smsCodeAuthenticationSecurityConfig)
                    .and()
                .apply(woodfishSocialConfigurer)
                    .and()
                .authorizeRequests()
                .antMatchers(
                        SecurityConstants.DEFAULT_AUTHENTICATION_URL,
                        SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE,
                        SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*",
                        securityProperties.getBrowser().getLoginPage()
                ).permitAll().anyRequest()
                .authenticated()
                .and()
                .csrf().disable();
    }




}
