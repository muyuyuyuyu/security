package com.woodfish.security.validate.properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidateCodePropertiesConfig {

    @Bean
    public ValidateCodeProperties validateCodeProperties(){
        return new ValidateCodeProperties();
    }

    @Bean
    public ImageCodeProperties imageCodeProperties(){
        return new ImageCodeProperties();
    }
}
