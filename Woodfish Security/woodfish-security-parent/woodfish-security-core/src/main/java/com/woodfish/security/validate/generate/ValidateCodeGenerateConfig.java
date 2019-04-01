package com.woodfish.security.validate.generate;

import com.woodfish.security.properties.SecurityProperties;
import com.woodfish.security.validate.entities.ImageCode;
import com.woodfish.security.validate.entities.ValidateCode;
import com.woodfish.security.validate.generate.impl.ImageValidateCodeGenerate;
import com.woodfish.security.validate.generate.impl.SmsValidateCodeGenerate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 添加配置类 判断容器中是否具有自定义的配置
 */
@Configuration
public class ValidateCodeGenerateConfig {
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * ImageCodeGenerate  图片验证码的匹配器
     * 如果当前容器中没有这个匹配器的bean 采用默认的配置
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(ImageValidateCodeGenerate.class)
    public ValidateCodeGenerate<ImageCode> imageCodeGenerate(){
        // 通过构造器 构造一个验证码构造器
        return new ImageValidateCodeGenerate(securityProperties.getCode().getImageCodeProperties());
    }

    @Bean
    @ConditionalOnMissingBean(SmsValidateCodeGenerate.class)
    public ValidateCodeGenerate<ValidateCode> smsCodeGenerate(){
        return new SmsValidateCodeGenerate(securityProperties.getCode());
    }
}
