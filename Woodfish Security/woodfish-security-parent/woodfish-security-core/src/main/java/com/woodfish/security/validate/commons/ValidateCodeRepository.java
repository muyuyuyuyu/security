package com.woodfish.security.validate.commons;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface ValidateCodeRepository<C> {
    /**
     * 创建验证码
     * @return
     */
    void save(HttpServletRequest request, String type, String code) throws Exception;
    String get(HttpServletRequest request,String code);
    void remove(HttpServletRequest request, String code);
}
