package com.woodfish.security.validate.processor;

import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface ValidateCodeProcessor<T> {
    // session的前缀
    void createCode(ServletWebRequest request) throws Exception;
    boolean validate(HttpServletRequest request);
}
