package com.woodfish.security.validate.processor;

import com.woodfish.security.validate.processor.sender.DefaultSmsCodeSender;
import com.woodfish.security.validate.entities.ValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

@Component
public class SmsValidateCodeProcessor extends AbstractValidateProcessor<ValidateCode>{

    @Autowired
    private DefaultSmsCodeSender defaultSmsCodeSender;

    @Override
    public void sendCode(ServletWebRequest request, ValidateCode validateCode) throws Exception {
        defaultSmsCodeSender.send(ServletRequestUtils.getRequiredStringParameter(request.getRequest(),"mobile"), validateCode.getCode());
    }
}
