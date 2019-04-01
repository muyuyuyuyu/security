package com.woodfish.security.validate.processor;

import com.woodfish.security.validate.entities.ImageCode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ImageValidateCodeProcessor extends AbstractValidateProcessor<ImageCode> {
    @Override
    public void sendCode(ServletWebRequest request, ImageCode imageCode) throws IOException {
        HttpServletResponse response = request.getResponse();
        response.setContentType("image/jpeg");
        //  不缓存验证码图像
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
    }
}
