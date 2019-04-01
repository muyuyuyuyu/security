package com.woodfish.security.authentication.mobile.provider;

import com.woodfish.security.authentication.mobile.token.SmsAuthenticationToken;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 自定義一個短信验证码理器
 */
@Getter
@Setter
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取短信验证的认证令牌
        SmsAuthenticationToken smsAuthenticationToken = (SmsAuthenticationToken) authentication;
        // 这里其实获取的是是手机号
        UserDetails userMobile = userDetailsService.loadUserByUsername((String) smsAuthenticationToken.getPrincipal());
        if(userMobile == null){
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }
        // 重新定义一个用户信息
        SmsAuthenticationToken smsAuthenticationTokenResult = new SmsAuthenticationToken(userMobile.getUsername(), userMobile.getAuthorities());
        smsAuthenticationTokenResult.setDetails(smsAuthenticationToken.getDetails());;
        return smsAuthenticationTokenResult;
    }

    /**
     * 判定传进来的token是否是SmsAuthenticationToken 类型
     * @param aClass
     * @return
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return SmsAuthenticationToken.class.isAssignableFrom(aClass);
    }


}
