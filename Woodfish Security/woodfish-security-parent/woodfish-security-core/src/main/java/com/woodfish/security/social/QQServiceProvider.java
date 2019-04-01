package com.woodfish.security.social;

import com.woodfish.security.social.qq.QQ;
import com.woodfish.security.social.qq.QQImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;

public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {
    public static final String URL_AUTHORIZE = "https://graph.qq.com/oauth2.0/authorize";

    public static final String URL_ACCESS_TOKEN = "https://graph.qq.com/oauth2.0/token";


    private String appId;

    public QQServiceProvider(String appId,String appSecret) {
        super(new OAuth2Template(appId,appSecret,URL_AUTHORIZE,URL_ACCESS_TOKEN));
        this.appId = appId;
    }

    @Override
    public QQ getApi(String accessToken) {
        return new QQImpl(accessToken, appId);
    }
}
