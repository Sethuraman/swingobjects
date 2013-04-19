package org.aesthete.swingobjects.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class EncoderDecoder {

    public static final Logger LOGGER = Logger.getLogger(EncoderDecoder.class);

    private static String secret = "#1234CsmartKeyTo"; // secret key length must be 16
    private static SecretKey key;
    private static Cipher cipher;
    private static Base64 coder;

    static {
        try {
            key = new SecretKeySpec(secret.getBytes(), "AES");
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding", "SunJCE");
            coder = new Base64();
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    public static synchronized String encrypt(String plainText) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] cipherText = cipher.doFinal(plainText.getBytes());
            return new String(coder.encode(cipherText));
        } catch (Exception e) {
            LOGGER.error("", e);
            throw new RuntimeException(e);
        }
    }

    public static synchronized String decrypt(String codedText) {
        try {
            byte[] encypted = coder.decode(codedText.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decrypted = cipher.doFinal(encypted);
            return new String(decrypted);
        } catch (Exception e) {
            LOGGER.error("", e);
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println(decrypt("cZmTg33/82w3zwi36tfR1A=="));
    }

}
