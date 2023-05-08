package com.song.pzforestserver.util;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

public class WeChatDecryptUtil {
    public static String decrypt(String encryptedData, String sessionKey, String iv) throws Exception {
        byte[] encryptedDataBytes = Base64.decodeBase64(encryptedData);
        byte[] sessionKeyBytes = Base64.decodeBase64(sessionKey);
        byte[] ivBytes = Base64.decodeBase64(iv);

        Key key = new SecretKeySpec(sessionKeyBytes, "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(ivBytes));

        byte[] decryptedData = cipher.doFinal(encryptedDataBytes);

        return new String(decryptedData);
    }
}
