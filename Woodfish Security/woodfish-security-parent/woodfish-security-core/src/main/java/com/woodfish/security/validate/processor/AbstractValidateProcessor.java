package com.woodfish.security.validate.processor;

import com.woodfish.security.validate.commons.ValidateCodeRepository;
import com.woodfish.security.validate.entities.ValidateCode;
import com.woodfish.security.validate.exception.ValidateCodeException;
import com.woodfish.security.validate.generate.ValidateCodeGenerate;
import com.woodfish.security.validate.commons.ValidateCodeType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
public abstract class AbstractValidateProcessor<C extends ValidateCode> implements ValidateCodeProcessor {
    /**
     * 其中 ValidateCodeGenerate是一个接口
     * 在Spring中使用map和Autowired spring会把容器中所有实现了该接口的bean存到MAP中
     * 其中map的key是Bean的name
     */
    @Autowired
    private Map<String, ValidateCodeGenerate> validateCodeGenerates;

    @Autowired
    private ValidateCodeRepository validateCodeRepository;
    /**
     * 发送验证码的逻辑不通
     * @param request
     * @param validateCode
     * @throws Exception
     */
    public abstract void sendCode(ServletWebRequest request, C validateCode) throws Exception;

    @Override
    public void createCode(ServletWebRequest request) throws Exception {
        // 创建验证码
        C validateCode = generator(request);
        // 保存验证码到session中
        validateCodeRepository.save(request.getRequest(),getValidateCodeType().getParamNameOnValidate(),validateCode.getCode());
        // 发送验证码的方法 由于方法不同
        sendCode(request, validateCode);
    }

    /**
     * 生成一个验证码
     *
     * @param request
     * @return
     */
    private C generator(ServletWebRequest request) throws ServletRequestBindingException {
        String type = getValidateCodeType().toString().toLowerCase();
        ValidateCodeGenerate validateCodeGenerate = validateCodeGenerates.get(type + ValidateCodeGenerate.class.getSimpleName());
        return (C) validateCodeGenerate.generate(request.getRequest());
    }

    /**
     * 分辨验证码的是哪种验证码
     *
     * @param
     * @return
     */
    private ValidateCodeType getValidateCodeType() {
        String type = StringUtils.substringBefore(getClass().getSimpleName(),"ValidateCodeProcessor");
        // 分割出是何种类型的请求 一般请求的方式放在code后面
        return ValidateCodeType.valueOf(type.toUpperCase());
    }

    @Override
    public boolean validate(HttpServletRequest request){
        // 获取session内部存储的验证码
        String type = getValidateCodeType().getParamNameOnValidate();
        String codeInSession = validateCodeRepository.get(request,type);
        log.info("这个东西是个什么鬼玩意"+type);
        // 又是一个spring中的工具类，
        String codeInRequest = null;
        try {
            codeInRequest = ServletRequestUtils.getStringParameter(request, type.toLowerCase()+"Code");
        } catch (ServletRequestBindingException e) {
            throw new ValidateCodeException("获取验证码的值失败");
        }

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException("验证码的值不能为空");
        }
        if (codeInRequest == null) {
            throw new ValidateCodeException("验证码错误");
        }
        if (!StringUtils.equals(codeInSession, codeInRequest)) {
            throw new ValidateCodeException("验证码不匹配");
        }

        if (!StringUtils.equals(codeInSession, codeInRequest)) {
            throw new ValidateCodeException("验证码不匹配");
        }
        // 验证码匹配成功清除session中的验证码
        validateCodeRepository.remove(request, type);
        return true;
    }

}
