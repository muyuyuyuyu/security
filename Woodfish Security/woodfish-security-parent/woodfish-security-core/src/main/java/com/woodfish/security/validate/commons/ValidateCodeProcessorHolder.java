package com.woodfish.security.validate.commons;

import com.woodfish.security.validate.processor.ValidateCodeProcessor;
import com.woodfish.security.validate.exception.ValidateCodeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ValidateCodeProcessorHolder {
    // ValidateProcessor 被实现的子存进这个Map内部
    @Autowired
    private Map<String, ValidateCodeProcessor> validateProcessorMap;

    public ValidateCodeProcessor findValidateCodeProcessor(ValidateCodeType type){
        return findValidateCodeProcessor(type.toString().toLowerCase());
    }

    public ValidateCodeProcessor findValidateCodeProcessor(String type){
        // 获取type对应的构造器
        String validateProcessorName = type.toLowerCase()+ValidateCodeProcessor.class.getSimpleName();
        System.out.println("validateProcessorName ==== "+validateProcessorName);
        ValidateCodeProcessor validateProcessor = validateProcessorMap.get(validateProcessorName);
        if(validateProcessor == null){
            throw new ValidateCodeException("验证码处理器" + validateProcessorName + "不存在");
        }
        return validateProcessor;
    }
}
