package com.woodfish.security.validate.properties;

import lombok.Data;

/**
 * 可以配置需要验证码的接口
 */
@Data
public class ImageCodeProperties extends ValidateCodeProperties{

    private int width = 67;
    private int height = 23;

    public ImageCodeProperties() {
        // 初始化验证码的长度为4
        super.setLength(4);
    }
}
