//
// Source entities recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.woodfish.security.authentication.mobile.token;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import javax.security.auth.Subject;

public class SmsAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = 500L;
    // 验证手机号
    private final String principal;

    public SmsAuthenticationToken(String mobile) {
        super((Collection)null);
        this.principal = mobile;
        this.setAuthenticated(false);
    }

    public SmsAuthenticationToken(String mobile, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = mobile;
        super.setAuthenticated(true);
    }


    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public boolean implies(Subject subject) {
        return false;
    }

    public Object getPrincipal() {
        return this.principal;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        } else {
            super.setAuthenticated(false);
        }
    }

}
