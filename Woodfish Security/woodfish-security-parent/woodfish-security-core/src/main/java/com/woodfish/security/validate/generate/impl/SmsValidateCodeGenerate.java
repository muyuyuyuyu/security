package com.woodfish.security.validate.generate.impl;

import com.woodfish.security.properties.SecurityProperties;
import com.woodfish.security.validate.generate.ValidateCodeGenerate;
import com.woodfish.security.validate.entities.ValidateCode;
import com.woodfish.security.validate.properties.SmsCodeProperties;
import com.woodfish.security.validate.properties.ValidateCodeProperties;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;

import javax.servlet.http.HttpServletRequest;


/**
 * 短信验证码的构造器
 */
@Component("smsValidateCodeGenerate")
public class SmsValidateCodeGenerate implements ValidateCodeGenerate<ValidateCode> {

    private ValidateCodeProperties validateCodeProperties;

    public SmsValidateCodeGenerate(ValidateCodeProperties validateCodeProperties) {
        this.validateCodeProperties = validateCodeProperties;
    }

    @Override
    public ValidateCode generate(HttpServletRequest request) throws ServletRequestBindingException {
        int length = validateCodeProperties.getLength();
        String smsCode = RandomStringUtils.randomNumeric(length);
        System.out.println("生成的短信验证码：" + smsCode);
        return new ValidateCode(smsCode, validateCodeProperties.getExpireTime());
    }

}
