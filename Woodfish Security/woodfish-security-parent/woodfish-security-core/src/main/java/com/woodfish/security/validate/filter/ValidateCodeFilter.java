package com.woodfish.security.validate.filter;

import com.woodfish.security.properties.SecurityProperties;
import com.woodfish.security.validate.commons.ValidateCodeProcessorHolder;
import com.woodfish.security.validate.exception.ValidateCodeException;
import com.woodfish.security.utils.SecurityConstants;
import com.woodfish.security.validate.commons.ValidateCodeType;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * 自定义验证码匹配过滤器
 * OncePerRequestFilter 只过滤一次
 */
@Component("validateCodeFilter")
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {


    @Qualifier("woodfishAuthenticationFailureHandler")
    @Autowired
    private AuthenticationFailureHandler failureHandler;


    private Map<String, ValidateCodeType> requestCodeUrlMap = new HashMap<>();
    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;
    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private AntPathMatcher pathMatcher;
    /**
     * 读取了配置文件信息之后 将所有需要验证码服务的URL存入到map中
     * @throws ServletException
     */
    @Override
    public void afterPropertiesSet() throws ServletException {

        super.afterPropertiesSet();
        // 第一步先将默认需要使用图片验证码的url存入到map中
        requestCodeUrlMap.put(SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_FORM,ValidateCodeType.IMAGE );
        // 第二步将配置文件中配置的需要验证码的链接存入到map中

        if(securityProperties.getCode().getImageCodeProperties().getUrl() != null)
            addUrlMap(securityProperties.getCode().getImageCodeProperties().getUrl(), ValidateCodeType.IMAGE);

        requestCodeUrlMap.put(SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE,ValidateCodeType.SMS );
        addUrlMap(securityProperties.getCode().getUrl(), ValidateCodeType.SMS);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ValidateCodeType validateCodeType = getValidateCodeType(request);
        if(validateCodeType != null){
            try {
                // 校验验证码
                validateCodeProcessorHolder.findValidateCodeProcessor(validateCodeType)
                        .validate(request);
            } catch (ValidateCodeException e) {
                // 校验失败
                failureHandler.onAuthenticationFailure(request,response ,e);
                return;
            }
        }
        doFilter(request,response ,filterChain );
    }

    void addUrlMap(String urlString,ValidateCodeType type){
        /**
         * 判断请求的url是否为空
         * 不为空存入我们定义的map中
         */
        if(StringUtils.isNotBlank(urlString)){
            String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(urlString, ",");
            for (String url:
                 urls) {
                requestCodeUrlMap.put(url, type);
            }
        }
    }

    private ValidateCodeType getValidateCodeType(HttpServletRequest request){
        ValidateCodeType codeType = null;
        // 判断是否是get方式的请求
        if(!StringUtils.equalsIgnoreCase(request.getMethod(), "get")){
            Set<String> urls = requestCodeUrlMap.keySet();
            for (String url:
                 urls) {
                // 判定请求的地址是否与定义的地址匹配
                if(pathMatcher.match(url,request.getRequestURI())){
                    codeType = requestCodeUrlMap.get(url);
                }
            }
        }
        return codeType;
    }

}
