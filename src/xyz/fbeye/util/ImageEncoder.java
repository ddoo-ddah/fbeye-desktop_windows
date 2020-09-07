/*
 * ImageEncoder.java
 * Author : susemeeee
 * Created Date : 2020-09-07
 */
package xyz.fbeye.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

public class ImageEncoder {
    public static String encode(BufferedImage image){
        String result = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, "png", bos);
            byte[] imageBytes = bos.toByteArray();

            result = Base64.getEncoder().encodeToString(imageBytes);
            bos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
