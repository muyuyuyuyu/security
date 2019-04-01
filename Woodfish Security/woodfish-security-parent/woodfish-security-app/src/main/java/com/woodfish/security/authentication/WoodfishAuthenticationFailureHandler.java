package com.woodfish.security.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woodfish.security.properties.SecurityProperties;
import com.woodfish.security.utils.HttpResult;
import com.woodfish.security.utils.enums.LoginResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 登录失败处理
 */
@Component("woodfishAuthenticationFailureHandler")
public class WoodfishAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    SecurityProperties securityProperties;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException exception) throws IOException, ServletException {
        logger.info("登录失败");
        // 登录失败 进入此处进行失败信息返回
        if (LoginResponseType.JSON.equals(securityProperties.getBrowser().getLoginType())) {
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            // 打印自定义的错误信息
            httpServletResponse.getWriter().write(objectMapper.writeValueAsString(HttpResult.isOk(exception.getMessage())));
        } else {
            super.onAuthenticationFailure(httpServletRequest, httpServletResponse, exception);
        }

    }
}
