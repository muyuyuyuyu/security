package com.woodfish.security.social.config;

import com.woodfish.security.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

@Slf4j
@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private SecurityProperties securityProperties;
    /**
     *
     * @param connectionFactoryLocator
     * @return
     */
    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        JdbcUsersConnectionRepository jdbcUsersConnectionRepository = new JdbcUsersConnectionRepository(
                dataSource, connectionFactoryLocator, Encryptors.noOpText());
        log.info("----------------------这个连接有没有创建");
//        jdbcUsersConnectionRepository.setTablePrefix(); 指定表的前缀
        return jdbcUsersConnectionRepository;
    }
    @Bean
    public SpringSocialConfigurer woodfishSocialConfigurer(){
        log.info("securityProperties.getSocial().getFilterProcessesUrl(): "+securityProperties.getSocial().getFilterProcessesUrl());
        return new WoodfishSpringSocialConfigurer(securityProperties.getSocial().getFilterProcessesUrl());
        /*SpringSocialConfigurer springSocialConfigurer = new SpringSocialConfigurer();
        springSocialConfigurer.postLoginUrl("https://www.baidu.com");
        return springSocialConfigurer;*/
    }

    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }

    @Bean
    public ConnectController connectController(
            ConnectionFactoryLocator connectionFactoryLocator,
            ConnectionRepository connectionRepository) {
        return new ConnectController(connectionFactoryLocator, connectionRepository);
    }
}
