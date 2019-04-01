package com.woodfish.security.validate.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 配置包含 浏览器的配置
 * 短信验证码的配置
 */
@Getter
@Setter
public class ValidateCodeProperties {
    private int length = 6;
    private String url;
    private int expireTime = 60;
    @Autowired
    private ImageCodeProperties imageCodeProperties;

}
