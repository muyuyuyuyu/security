package com.woodfish.security.social.qq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woodfish.security.controller.ValidateCodeController;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.io.IOException;

/**
 * 实现spring-social中的 OAuth2协议
 */
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {

    private static Logger logger = LoggerFactory.getLogger(ValidateCodeController.class);

    // 获取 QQ OPENID 授权码的地址
    private final static String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";

    // 获取QQ用户信息的地址
    private final static String URL_GET_USER_INFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";
    // 去QQ申请的appId
    private String appId;
    // 用户ID和QQ号码一一对应
    private String openId;

    @Autowired
    private ObjectMapper objectMapper;

    public QQImpl(String accessToken,String appId) {
        // QQ默认是将信息放在参数中 accessToken授权令牌
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.appId = appId;
        String openIdUrl = String.format(URL_GET_OPENID,accessToken);
        String result = getRestTemplate().getForObject(openIdUrl,String.class);
        logger.info("有没有去请求这个逼？"+result);
        this.openId = StringUtils.substringBetween(result, "\"openid\":\"", "\"}");
    }

    @Override
    public QQUserInfo getUserInfo() {
        // 拼接获取用户信息的url
        String url = String.format(URL_GET_USER_INFO,appId,openId);
        // 此处使用 spring-RestTemplate发送请求获取数据
        String userInfo = getRestTemplate().getForObject(url,String.class);

        logger.info("获取的用户信息是："+userInfo);
        QQUserInfo qqUserInfo = null;
        try {
            qqUserInfo = objectMapper.readValue(userInfo, QQUserInfo.class);
            qqUserInfo.setOpenId(openId);
        } catch (IOException e) {
            throw new RuntimeException("用户认证失败",e);
        }
        return qqUserInfo;
    }
}
