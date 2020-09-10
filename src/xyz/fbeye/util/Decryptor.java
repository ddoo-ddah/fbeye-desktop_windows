/*
 * Decriptor.java
 * Author : susemeeee
 * Created Date : 2020-08-20
 */
package xyz.fbeye.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

public class Decryptor {
    public static String decrypt(String str, String key){
        try{
            byte[] iv = new byte[16];
            Arrays.fill(iv, (byte) 0x0);
            byte[] keyBytes = new byte[16];
            byte[] b = Base64.getDecoder().decode(key);
            int len = b.length;
            if(len > keyBytes.length){
                len = keyBytes.length;
            }
            System.arraycopy(b, 0, keyBytes, 0, len);
            SecretKeySpec keySpec = new SecretKeySpec(b, "AES");

            Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
            c.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv));
            byte[] byteStr = org.apache.commons.codec.binary.Base64.decodeBase64(str.getBytes());
            return new String(c.doFinal(byteStr), StandardCharsets.UTF_8);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
