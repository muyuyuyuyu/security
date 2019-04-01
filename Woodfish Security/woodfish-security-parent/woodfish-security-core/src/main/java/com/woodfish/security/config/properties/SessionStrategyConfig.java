package com.woodfish.security.config.properties;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;

@Configuration
public class SessionStrategyConfig {

    /**
     * 当没有使用 redis作为共享Session时
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(RedisTemplate.class)
    public SessionStrategy sessionStrategy(){
        return new HttpSessionSessionStrategy();
    }
}
