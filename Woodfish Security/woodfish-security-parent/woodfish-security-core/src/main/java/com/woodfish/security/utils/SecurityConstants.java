package com.woodfish.security.utils;

/**
 * 静态常量字符串接口 包含各种常用的接口url
 */
public interface SecurityConstants {
    /**
     *  默认获取验证码服务的接口前缀
     */
    String DEFAULT_VALIDATE_CODE_URL_PREFIX = "/code";

    /**
     *  身份验证，跳转入口
     */
    String DEFAULT_AUTHENTICATION_URL ="/authentication/required";
    /**
     * 用户名密码登录调用的接口
     */
    String DEFAULT_SIGN_IN_PROCESSING_URL_FORM = "/authentication/form";

    /**
     * 短信验证码登录调用的接口
     */
    String DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE = "/authentication/mobile";

    /**
     * 默认的登录页路径
     */
    String DEFAULT_SIGN_IN_PAGE_URL = "/woodfish-login.html";

    /**
     * 验证图片验证码时，http请求中默认的携带图片验证码信息的参数的名称
     */
    String DEFAULT_PARAMETER_NAME_CODE_IMAGE = "image";
    /**
     * 验证短信验证码时，http请求中默认的携带短信验证码信息的参数的名称
     */
    String DEFAULT_PARAMETER_NAME_CODE_SMS = "sms";
    /**
     * 发送短信验证码 或 验证短信验证码时，传递手机号的参数的名称
     */
    String DEFAULT_PARAMETER_NAME_MOBILE = "mobile";
}
