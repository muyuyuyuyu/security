package com.woodfish.security.validate.entities;

import lombok.Data;

import java.awt.image.BufferedImage;

@Data
public class ImageCode extends ValidateCode{
    private BufferedImage image;

    public ImageCode() {
    }

    public ImageCode(BufferedImage image, String code, int expireIn) {
        super(code,expireIn);
        this.image = image;

    }

}
