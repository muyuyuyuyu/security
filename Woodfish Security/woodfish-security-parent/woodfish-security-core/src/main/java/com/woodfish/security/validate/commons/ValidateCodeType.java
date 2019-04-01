package com.woodfish.security.validate.commons;

import com.woodfish.security.utils.SecurityConstants;

/**
 * 请求验证码类型枚举类
 */
public enum ValidateCodeType {
    /**
     * 短信验证码入口
     */
    SMS{
        @Override
        public String getParamNameOnValidate(){
            return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_SMS;
        }
    },
    /**
     * 图片验证码入口
     */
    IMAGE{
        @Override
        public String getParamNameOnValidate(){
            return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_IMAGE;
        }
    };

    public abstract String getParamNameOnValidate();
}
