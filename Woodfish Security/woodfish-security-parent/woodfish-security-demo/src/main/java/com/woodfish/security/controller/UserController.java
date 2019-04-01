package com.woodfish.security.controller;

import com.woodfish.security.properties.SecurityProperties;
import com.woodfish.security.model.User;
import com.woodfish.security.utils.HttpResult;
import com.woodfish.security.utils.enums.HttpStatusEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    // 封装了引发跳转请求的工具类，看实现类应该是从session中获取的
    @Autowired
    private RequestCache requestCache;

    @Autowired
    private RedirectStrategy redirectStrategy;

    @Autowired
    private SecurityProperties securityProperties;

    @GetMapping("/list")
    public List<String> listUser(){
        List<String> userList = new ArrayList<>();
        userList.add("用户1");
        return userList;
    }

    // getMapping不支持requestBody
    @PostMapping("/login")
    public String findUser(@RequestBody User user){
        System.out.println(user);
        return "success";
    }

    @RequestMapping("/authentication/required")
    public HttpResult requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SavedRequest savedRequest = requestCache.getRequest(request,response);
        // 如果引发认证的请求
        // Spring 在跳转前应该会把信息存在某个地方
        if(savedRequest != null){
            // 获取跳转链接的地质
            String targetUrl = savedRequest.getRedirectUrl();
            logger.info("引发跳转的请求：" + targetUrl);
            // 如果是html请求，则跳转到登录页
            if (StringUtils.endsWithIgnoreCase(targetUrl, ".html")) { // 此处可以自定义访问的策略
//                redirectStrategy.sendRedirect(request, response, "/woodfish-login.html");
                redirectStrategy.sendRedirect(request, response,securityProperties.getBrowser().getLoginPage());
            }
        }
        // 否则都返回需要认证的json串
        return new HttpResult(HttpStatusEnum.UNAUTHORIZED,"访问的服务需要身份认证，请引导用户到登录页",savedRequest.getRedirectUrl());
    }
}

