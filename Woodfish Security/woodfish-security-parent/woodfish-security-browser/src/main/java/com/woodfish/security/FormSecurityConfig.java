package com.woodfish.security;

import com.woodfish.security.utils.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class FormSecurityConfig {
    @Autowired
    private AuthenticationSuccessHandler woodfishAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler woodfishAuthenticationFailureHandler;

    public void applyPasswordLoginAuthenticationConfig(HttpSecurity http) throws Exception {

        http.formLogin()
                .loginPage(SecurityConstants.DEFAULT_AUTHENTICATION_URL)
                .loginProcessingUrl(SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_FORM)
                .successHandler(woodfishAuthenticationSuccessHandler)
                .failureHandler(woodfishAuthenticationFailureHandler);

    }
}
