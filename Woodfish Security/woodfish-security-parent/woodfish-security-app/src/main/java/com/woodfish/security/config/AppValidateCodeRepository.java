package com.woodfish.security.config;

import com.woodfish.security.validate.commons.ValidateCodeRepository;
import com.woodfish.security.validate.exception.ValidateCodeException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@Component
public class AppValidateCodeRepository implements ValidateCodeRepository {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void save(HttpServletRequest request, String type, String code) throws Exception {
        stringRedisTemplate.opsForValue().set(builderKey(request),code,5 , TimeUnit.MINUTES);
    }

    @Override
    public String get(HttpServletRequest request, String code) {
        return stringRedisTemplate.opsForValue().get(builderKey(request));
    }

    /**
     * 清除缓存
     * @param request
     * @param code
     */
    @Override
    public void remove(HttpServletRequest request, String code) {
        stringRedisTemplate.delete(builderKey(request));
    }

    /**
     * 构造一个key key前缀加上设备ID
     * @param request
     * @return
     */
    private String builderKey(HttpServletRequest request){
        String deviceId = request.getHeader("deviceId");
        if(StringUtils.isBlank(deviceId)){
            throw new ValidateCodeException("设备ID不能为空");
        }
        return "APP_CODE_" + deviceId;
    }
}
