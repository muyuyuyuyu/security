package com.woodfish.security.valicate;

import com.woodfish.security.properties.SecurityProperties;
import com.woodfish.security.validate.commons.ValidateCodeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
@Slf4j
@Component
public class BrowserValidateCodeRepository<C> implements ValidateCodeRepository {
    private static final String CODE_KEY = "_CODE_KEY";

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void save(HttpServletRequest request, String type, String code) throws Exception {
        httpSession(request).setMaxInactiveInterval(securityProperties.getCode().getExpireTime());
        httpSession(request).setAttribute(type.toUpperCase()+CODE_KEY,code);
    }

    @Override
    public String get(HttpServletRequest request, String type) {
       return (String) httpSession(request).getAttribute(type.toUpperCase()+CODE_KEY);
    }

    @Override
    public void remove(HttpServletRequest request, String type) {
        httpSession(request).removeAttribute(type.toUpperCase()+CODE_KEY);
    }
    private HttpSession httpSession(HttpServletRequest request){
        return request.getSession();
    }
}
