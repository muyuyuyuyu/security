package com.woodfish.security.validate.generate;

import org.springframework.web.bind.ServletRequestBindingException;

import javax.servlet.http.HttpServletRequest;

public interface ValidateCodeGenerate<T> {
    T generate(HttpServletRequest request) throws ServletRequestBindingException;
}
