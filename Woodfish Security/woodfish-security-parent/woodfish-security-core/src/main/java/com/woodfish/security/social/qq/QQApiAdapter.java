package com.woodfish.security.social.qq;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

public class QQApiAdapter implements ApiAdapter<QQ> {
    /**
     * 判定连接是够可用 默认腾讯不会倒闭
     * @param qq
     * @return
     */
    @Override
    public boolean test(QQ qq) {
        return true;
    }

    /**
     * 适配数据spring-social
     * @param qqApi
     * @param values
     */
    @Override
    public void setConnectionValues(QQ qqApi, ConnectionValues values) {
        QQUserInfo userInfo = qqApi.getUserInfo();
        values.setDisplayName(userInfo.getNickname());
        // 配置头像
        values.setImageUrl(userInfo.getFigureurl_qq_1());
        // 有主页的应用需要配置主页地址 比如新浪微博
        values.setProfileUrl(null);
        // 服务提供商返回的该user的openid
        // 一般来说这个openid是和你的开发账户也就是appid绑定的
        values.setProviderUserId(userInfo.getOpenId());
    }

    @Override
    public UserProfile fetchUserProfile(QQ qqApi) {
        return null;
    }

    @Override
    public void updateStatus(QQ qqApi, String s) {

    }
}
