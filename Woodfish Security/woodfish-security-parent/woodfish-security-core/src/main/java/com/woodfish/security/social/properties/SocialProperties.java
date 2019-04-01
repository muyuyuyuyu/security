package com.woodfish.security.social.properties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SocialProperties {

    private String filterProcessesUrl;
    private QQProperties QQ = new QQProperties();
}

