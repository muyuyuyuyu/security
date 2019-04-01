package com.woodfish.security.authentication.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * @PackageName: com.woodfish.security.authentication.properties
 * @Description:
 * @author: woodfish
 * @date: 2019/3/30
 */
@Setter
@Getter
public class OAuth2ClientProperties {
    /**
     *         private final String clientId;
     *         private Collection<String> authorizedGrantTypes;
     *         private Collection<String> authorities;
     *         private Integer accessTokenValiditySeconds;
     *         private Integer refreshTokenValiditySeconds;
     *         private Collection<String> scopes;
     *         private Collection<String> autoApproveScopes;
     *         private String secret;
     *         private Set<String> registeredRedirectUris;
     *         private Set<String> resourceIds;
     *         private boolean autoApprove;
     *         private Map<String, Object> additionalInformation;
     */
    private String clientId;
    private String clientSecret;
    private String[] authorizedGrantTypes = {};
    private String[] redirectUris = {}; // 信任的回调域
    private String[] scopes = {};
    private int accessTokenValiditySeconds; // token有效期
}
