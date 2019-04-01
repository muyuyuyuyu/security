package com.woodfish.security.social.config;

import com.woodfish.security.properties.SecurityProperties;
import com.woodfish.security.social.properties.QQProperties;
import com.woodfish.security.social.qq.QQOAuth2ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;


@Slf4j
@Configuration
// 只有配置了APP-Id时才会启动这个配置类
@ConditionalOnProperty(prefix = "woodfish.security.social.qq", name = "appId")
public class QQAutoConfig extends SocialConfigurerAdapter {

    private final static Logger logger = LoggerFactory.getLogger(QQAutoConfig.class);
    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {
        connectionFactoryConfigurer.addConnectionFactory(createConnectionFactory());
    }

    /**
     * 请务必配置好连接工厂不然无法连接到对接的服务器
     * @return
     */
    public ConnectionFactory<?> createConnectionFactory() {
        log.info("创建QQConnectFactory成功");
        QQProperties qq = securityProperties.getSocial().getQQ();
        return new QQOAuth2ConnectionFactory(qq.getProviderId(), qq.getAppId(), qq.getAppSecret());
    }

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        return null;
    }

}
