package com.woodfish.security.properties;

import com.woodfish.security.authentication.properties.OAuth2Properties;
import com.woodfish.security.social.properties.QQProperties;
import com.woodfish.security.social.properties.SocialProperties;
import com.woodfish.security.validate.properties.ValidateCodeProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 总配置类
 * @ConfigurationProperties(prefix = "woodfish.security") 去拿这个前缀的配置
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "woodfish.security")
public class SecurityProperties {

    @Qualifier(value = "browserProperties")
    @Autowired
    private BrowserProperties browser;
    /**
     * 验证码的配置
     */
    @Qualifier(value = "validateCodeProperties")
    @Autowired
    private ValidateCodeProperties code;

    private SocialProperties social = new SocialProperties();

    private OAuth2Properties oAuth2Properties = new OAuth2Properties();
}
