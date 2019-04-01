package com.woodfish.security.config.properties;

import com.woodfish.security.properties.BrowserProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BrowserPropertiesConfig {
    @Bean
    public BrowserProperties browserProperties(){
        return new BrowserProperties();
    }
}
