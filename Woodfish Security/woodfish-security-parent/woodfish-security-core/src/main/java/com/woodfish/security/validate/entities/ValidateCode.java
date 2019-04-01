package com.woodfish.security.validate.entities;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ValidateCode {
    private String code;
    private LocalDateTime codeExpireTime;

    public ValidateCode() {
    }

    public ValidateCode(String code, int expireTime) {
        this.code = code;
        this.codeExpireTime = LocalDateTime.now().plusSeconds(expireTime);
    }

    public boolean isExpire(){
        return this.codeExpireTime.isBefore(LocalDateTime.now());
    }
}
