package com.woodfish.security.controller;

import com.woodfish.security.utils.SecurityConstants;
import com.woodfish.security.validate.processor.ValidateCodeProcessor;
import com.woodfish.security.validate.commons.ValidateCodeProcessorHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码控制类
 */
@RestController
public class ValidateCodeController {
    private static Logger logger = LoggerFactory.getLogger(ValidateCodeController.class);
    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;


    /**
     * 使用rest风格确定要返回的验证码的类型
     * @param type 返回验证码的类型  type=image 图片验证码 mobile 手机验证码
     * @param request
     * @throws Exception
     */
    @GetMapping(SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX+"/{type}")
    public void createImageCode(@PathVariable("type") String type, ServletWebRequest request) throws Exception {
        ValidateCodeProcessor validateCodeProcessor= validateCodeProcessorHolder.findValidateCodeProcessor(type);
        validateCodeProcessor.createCode(request);
    }
}
