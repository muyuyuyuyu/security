package com.woodfish.security.validate.generate.impl;

import com.woodfish.security.validate.generate.ValidateCodeGenerate;
import com.woodfish.security.validate.entities.ImageCode;
import com.woodfish.security.validate.properties.ImageCodeProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

@Component("imageValidateCodeGenerate")
public class ImageValidateCodeGenerate implements ValidateCodeGenerate<ImageCode> {
   /* // 统一通过 全局配置获取 验证码配置器
    @Autowired
    private SecurityProperties securityProperties;*/

    private ImageCodeProperties imageCodeProperties;

    public ImageValidateCodeGenerate(ImageCodeProperties imageCodeProperties) {
        this.imageCodeProperties = imageCodeProperties;
    }

    @Override
    public ImageCode generate(HttpServletRequest request) throws ServletRequestBindingException {
        return createImageCode(request);
    }

    private ImageCode createImageCode(HttpServletRequest request) throws ServletRequestBindingException {
        // 生成4位随机数
        // 获取图片验证码配置类
//        ImageCodeProperties imageCodeProperties = securityProperties.getCode().getImageCodeProperties();
//         可以从请求中获取 可以配置的数据 使用spring的工具类 ServletRequestUtils 获取请求的数据
        // public static int getIntParameter(ServletRequest request, String name, int defaultVal) // 缺省值 从配置文件中获取
        int width = ServletRequestUtils.getIntParameter(request, "width", this.imageCodeProperties.getWidth());
        int height = ServletRequestUtils.getIntParameter(request, "height ", this.imageCodeProperties.getHeight());
        int length = ServletRequestUtils.getIntParameter(request, "length ", this.imageCodeProperties.getLength());
        int expireTime = ServletRequestUtils.getIntParameter(request, "expireTime", this.imageCodeProperties.getExpireTime());
        return createImageCode(width, height, expireTime);
    }

    /*private ImageCodeProperties imageCodeProperties(){
        return securityProperties.getCode().getImageCodeProperties();
    }*/

    /**
     * 生成验证码
     *
     * @param width
     * @param height
     * @param expireTime 过期时间
     * @return
     */
    private ImageCode createImageCode(int width, int height, int expireTime) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();

        Random random = new Random();

        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }

        String sRand = "";
        for (int i = 0; i < this.imageCodeProperties.getLength(); i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand += rand;
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(rand, 13 * i + 6, 16);
        }

        g.dispose();
        return new ImageCode(image, sRand, expireTime);
    }


    /**
     * 生成随机背景条纹
     *
     * @param fc
     * @param bc
     * @return
     */
    private Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

}
