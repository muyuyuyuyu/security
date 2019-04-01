package com.woodfish.security.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woodfish.security.properties.SecurityProperties;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

@Component("woodfishAuthenticationSuccessHandler")
public class WoodfishAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private static Logger logger = LoggerFactory.getLogger(WoodfishAuthenticationSuccessHandler.class);

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String header = request.getHeader("Authorization");
        if (header == null && header.toLowerCase().startsWith("basic ")) {
            throw new UnapprovedClientAuthenticationException("请求头格式错误，请检查你的请求头格式");
        }
        // 这个数组包含用户请求的clientId和 clientSecret
        String[] tokens = this.extractAndDecodeHeader(header, request);

        String clientId = tokens[0];
        String clientSecret = tokens[1];
        // 生成一个客户端信息
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);

        if (clientDetails == null) {
            throw new UnapprovedClientAuthenticationException("客户端ID不存在，ID：" + clientId);
        } else if (!StringUtils.equals(clientDetails.getClientSecret(), clientSecret)) {
            throw new UnapprovedClientAuthenticationException("clientSecret不匹配");
        }
        // 四种模式的请求 使用同一种的请求
        TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP, clientId, clientDetails.getScope(), "custom");
        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
        OAuth2Authentication auth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
        OAuth2AccessToken auth2AccessToken = authorizationServerTokenServices.createAccessToken(auth2Authentication);

        response.setContentType("application/json;charset=UTF-8");
        // 使用Json返回token
        response.getWriter().write(objectMapper.writeValueAsString(auth2AccessToken));
    }

    private String[] extractAndDecodeHeader(String header, HttpServletRequest request) throws IOException {
        byte[] base64Token = header.substring(6).getBytes("UTF-8");

        byte[] decoded;
        try {
            decoded = Base64.getDecoder().decode(base64Token);
        } catch (IllegalArgumentException var7) {
            throw new BadCredentialsException("Failed to decode basic authentication token");
        }

        String token = new String(decoded, "UTF-8");
        int delim = token.indexOf(":");
        if (delim == -1) { // 没有找到冒号的位置抛出异常
            throw new BadCredentialsException("basic认证信息错误，请检查");
        } else {
            return new String[]{token.substring(0, delim), token.substring(delim + 1)};
        }
    }
}
