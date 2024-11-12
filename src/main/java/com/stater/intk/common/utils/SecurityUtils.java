package com.stater.intk.common.utils;

import com.nimbusds.jose.jwk.RSAKey;
import com.stater.intk.model.exception.BusinessException;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Base64.getDecoder;
import static java.util.Base64.getEncoder;

public class SecurityUtils {
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

    public static String encrypt(String input, SecretKey key) {
        try {
            cipher.init(1, key);
            byte[] inputBytes = input.getBytes(UTF_8);
            byte[] cipherText = cipher.doFinal(inputBytes);
            byte[] message = new byte[12 + inputBytes.length + 16];
            System.arraycopy(cipher.getIV(), 0, message, 0, 12);
            System.arraycopy(cipherText, 0, message, 12, cipherText.length);
            return getEncoder().encodeToString(message);
        } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public static String decrypt(String cipherText, SecretKey secretKey) {
        try {
            byte[] encryptedText = getDecoder().decode(cipherText);
            cipher.init(2, secretKey, new GCMParameterSpec(128, encryptedText, 0, 12));
            byte[] plainText = cipher.doFinal(encryptedText, 12, encryptedText.length - 12);
            return new String(plainText, UTF_8);
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | BadPaddingException |
                 IllegalBlockSizeException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public static String encryptBase64(String input) {
        byte[] inputBytes = input.getBytes(UTF_8);
        return new String(Base64.getEncoder().encode(inputBytes), UTF_8);
    }

    public static String decryptBase64(String cipherText) {
        return new String(getDecoder().decode(cipherText.getBytes()));
    }

    public static SecretKey getSecretKey(String password, String salt) {
        try {
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(UTF_8), 65536, 256);
            return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
        } catch (InvalidKeySpecException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public static RSAKey generateRSAKey() {
        KeyPair keyPair = generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        return new RSAKey.Builder(publicKey).privateKey(privateKey).keyID(GeneralUtils.generateId(20, false)).build();
    }

    private static KeyPair generateKeyPair() {
        KeyPair keyPair;
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            keyPair = generator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new BusinessException(e.getMessage());
        }
        return keyPair;
    }
}
