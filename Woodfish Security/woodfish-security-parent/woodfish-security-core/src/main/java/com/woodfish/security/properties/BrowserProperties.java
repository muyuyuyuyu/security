package com.woodfish.security.properties;

import com.woodfish.security.utils.enums.LoginResponseType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
public class BrowserProperties {

    // 如果没有配置页面 就是使用默认的页面
    private String loginPage = "/woodfish-login.html";
    // 默认使用 Json返回
    private LoginResponseType loginType = LoginResponseType.JSON;


}
