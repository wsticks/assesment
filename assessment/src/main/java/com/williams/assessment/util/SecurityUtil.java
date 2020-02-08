package com.williams.assessment.util;

import com.williams.assessment.exception.ProcessingException;
import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityUtil {

    public static String hashWithMd5(String rawKey) throws ProcessingException {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] encrypted = md.digest(rawKey.getBytes());
            return new String(Hex.encodeHex(encrypted));
        } catch (NoSuchAlgorithmException ex) {
            String errorMessage = "Unable to hash this string";
            throw new ProcessingException(errorMessage);
        }
    }

    public static String hashWithSha256(String rawKey) throws ProcessingException {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] encrypted = md.digest(rawKey.getBytes());
            return new String(Hex.encodeHex(encrypted));
        } catch (NoSuchAlgorithmException ex) {
            String errorMessage = "Unable to hash this string";
            throw new ProcessingException(errorMessage);
        }
    }

    public static String hashWithHMACSHA1(String key, String data) throws ProcessingException {
        try {
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            return new String(Hex.encodeHex(mac.doFinal(data.getBytes(
                    Charsets.UTF_8))));
        } catch (NoSuchAlgorithmException ex) {
            String errorMessage = "Unable to hash this string";
            throw new ProcessingException(errorMessage);
        } catch (Exception ex) {
            String errorMessage = "Unable to hash this string";
            throw new ProcessingException(errorMessage);
        }
    }

    public static String hashWithHmacSha256(String key, String data) throws ProcessingException {
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            byte[] decodedPrivateKey = Hex.decodeHex(key.toCharArray());
            SecretKeySpec secret_key = new SecretKeySpec(decodedPrivateKey, "HmacSHA256");
            sha256_HMAC.init(secret_key);
            return Hex.encodeHexString(sha256_HMAC.doFinal(data.getBytes("UTF-8")));
        } catch (Exception ex) {
            String errorMessage = "Unable to hash this string";
            throw new ProcessingException(errorMessage);
        }
    }


}
