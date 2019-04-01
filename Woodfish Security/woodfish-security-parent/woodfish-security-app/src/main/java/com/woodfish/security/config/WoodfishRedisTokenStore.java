package com.woodfish.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.common.ExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.store.redis.JdkSerializationStrategy;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStoreSerializationStrategy;

import java.util.Date;

/**
 * @PackageName: com.woodfish.security.config
 * @Description:
 * @author: woodfish
 * @date: 2019/3/30
 */
@Setter
@Getter
public class WoodfishRedisTokenStore extends RedisTokenStore {

    private RedisTokenStoreSerializationStrategy serializationStrategy = new JdkSerializationStrategy();
    private RedisConnectionFactory connectionFactory;
    private String prefix = "";
    private AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();

    public WoodfishRedisTokenStore(RedisConnectionFactory connectionFactory) {
        super(connectionFactory);
    }

    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        byte[] serializedAccessToken = this.serialize((Object)token);
        byte[] serializedAuth = this.serialize((Object)authentication);
        byte[] accessKey = this.serializeKey("access:" + token.getValue());
        byte[] authKey = this.serializeKey("auth:" + token.getValue());
        byte[] authToAccessKey = this.serializeKey("auth_to_access:" + this.authenticationKeyGenerator.extractKey(authentication));
        byte[] approvalKey = this.serializeKey("uname_to_access:" + getApprovalKey(authentication));
        byte[] clientId = this.serializeKey("client_id_to_access:" + authentication.getOAuth2Request().getClientId());
        RedisConnection conn = this.getConnection();

        try {
            conn.openPipeline();
            conn.stringCommands().set(accessKey, serializedAccessToken);
            conn.set(accessKey, serializedAccessToken);
            conn.set(authKey, serializedAuth);
            conn.set(authToAccessKey, serializedAccessToken);
            if (!authentication.isClientOnly()) {
                conn.rPush(approvalKey, new byte[][]{serializedAccessToken});
            }

            conn.rPush(clientId, new byte[][]{serializedAccessToken});
            if (token.getExpiration() != null) {
                int seconds = token.getExpiresIn();
                conn.expire(accessKey, (long)seconds);
                conn.expire(authKey, (long)seconds);
                conn.expire(authToAccessKey, (long)seconds);
                conn.expire(clientId, (long)seconds);
                conn.expire(approvalKey, (long)seconds);
            }

            OAuth2RefreshToken refreshToken = token.getRefreshToken();
            if (refreshToken != null && refreshToken.getValue() != null) {
                byte[] refresh = this.serialize(token.getRefreshToken().getValue());
                byte[] auth = this.serialize(token.getValue());
                byte[] refreshToAccessKey = this.serializeKey("refresh_to_access:" + token.getRefreshToken().getValue());
                conn.set(refreshToAccessKey, auth);
                byte[] accessToRefreshKey = this.serializeKey("access_to_refresh:" + token.getValue());
                conn.set(accessToRefreshKey, refresh);
                if (refreshToken instanceof ExpiringOAuth2RefreshToken) {
                    ExpiringOAuth2RefreshToken expiringRefreshToken = (ExpiringOAuth2RefreshToken)refreshToken;
                    Date expiration = expiringRefreshToken.getExpiration();
                    if (expiration != null) {
                        int seconds = Long.valueOf((expiration.getTime() - System.currentTimeMillis()) / 1000L).intValue();
                        conn.expire(refreshToAccessKey, (long)seconds);
                        conn.expire(accessToRefreshKey, (long)seconds);
                    }
                }
            }

            conn.closePipeline();
        } finally {
            conn.close();
        }

    }

    private byte[] serialize(Object object) {
        return this.serializationStrategy.serialize(object);
    }

    private byte[] serializeKey(String object) {
        return this.serialize(this.prefix + object);
    }

    private RedisConnection getConnection() {
        return this.connectionFactory.getConnection();
    }

    private static String getApprovalKey(OAuth2Authentication authentication) {
        String userName = authentication.getUserAuthentication() == null ? "" : authentication.getUserAuthentication().getName();
        return getApprovalKey(authentication.getOAuth2Request().getClientId(), userName);
    }

    private static String getApprovalKey(String clientId, String userName) {
        return clientId + (userName == null ? "" : ":" + userName);
    }
}
