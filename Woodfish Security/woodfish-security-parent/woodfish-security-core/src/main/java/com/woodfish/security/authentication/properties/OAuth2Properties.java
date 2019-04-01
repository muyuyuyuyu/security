package com.woodfish.security.authentication.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * @PackageName: com.woodfish.security.authentication.properties
 * @Description: OAuth2资源服务器配置类
 * @author: woodfish
 * @date: 2019/3/30
 */
@Getter
@Setter
public class OAuth2Properties {
    // 可以多个认证客户端 需要用数组
    private OAuth2ClientProperties[] oAuth2ClientProperties = {};

}
