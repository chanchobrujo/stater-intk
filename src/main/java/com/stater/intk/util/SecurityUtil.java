package com.stater.intk.util;

import com.stater.intk.model.exception.BusinessException;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Base64.getDecoder;
import static java.util.Base64.getEncoder;

public class SecurityUtil {
    private final static Cipher cipher;
    private final static SecretKeyFactory factory;

    static {
        try {
            cipher = Cipher.getInstance("AES/GCM/NoPadding");
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public static String encryptString(String input, SecretKey key) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        cipher.init(1, key);
        byte[] inputBytes = input.getBytes(UTF_8);
        byte[] cipherText = cipher.doFinal(inputBytes);
        byte[] message = new byte[12 + inputBytes.length + 16];
        System.arraycopy(cipher.getIV(), 0, message, 0, 12);
        System.arraycopy(cipherText, 0, message, 12, cipherText.length);
        return getEncoder().encodeToString(message);
    }

    public static String decryptString(String cipherText, SecretKey secretKey) throws InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        byte[] encryptedText = getDecoder().decode(cipherText);
        cipher.init(2, secretKey, new GCMParameterSpec(128, encryptedText, 0, 12));
        byte[] plainText = cipher.doFinal(encryptedText, 12, encryptedText.length - 12);
        return new String(plainText, UTF_8);
    }

    public static SecretKey getSecretKeyFromPassword(String password, String salt) throws InvalidKeySpecException {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(UTF_8), 65536, 256);
        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
    }
}
