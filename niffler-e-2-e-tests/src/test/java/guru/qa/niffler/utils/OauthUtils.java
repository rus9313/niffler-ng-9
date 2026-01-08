package guru.qa.niffler.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;


public class OauthUtils {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    public static String generateCodeVerifier() {
        byte[] randomBytes = new byte[32];
        SECURE_RANDOM.nextBytes(randomBytes);
        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(randomBytes);
    }

    public static String generateCodeChallange(String codeVerifier) {
        byte[] bytes;
        MessageDigest messageDigest;
        try {
            bytes = codeVerifier.getBytes("US-ASCII");
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(bytes, 0, bytes.length);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] digest = messageDigest.digest();
        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(digest);
    }
}