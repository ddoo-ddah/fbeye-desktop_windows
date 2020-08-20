/*
 * Decriptor.java
 * Author : susemeeee
 * Created Date : 2020-08-20
 */
package com.FBEye.util;

import javax.crypto.Cipher;
import java.security.Key;
import java.util.Base64;

public class Decryptor {
    public static String decrypt(String str, Key key){
        try{
            Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
            c.init(Cipher.DECRYPT_MODE, key);
            return new String(c.doFinal(Base64.getDecoder().decode(str.getBytes())));
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
