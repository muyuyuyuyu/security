package com.woodfish.security.social.qq;

import com.woodfish.security.social.QQServiceProvider;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

public class QQOAuth2ConnectionFactory extends OAuth2ConnectionFactory<QQ> {

    /**
     *
     * @param providerId 服务上唯一标识
     * @param appId
     * @param secret
     */
    public QQOAuth2ConnectionFactory(String providerId, String appId, String appSecret) {
        super(providerId, new QQServiceProvider(appId,appSecret), new QQApiAdapter());
    }
}
