package com.woodfish.security.validate.processor.sender;

public interface SmsCodeSender {
    void send(String mobile,String code);
}
