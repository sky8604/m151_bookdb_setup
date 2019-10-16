package ch.bzz.book.util;

import ch.bzz.book.service.Config;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.core.NewCookie;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * utility for creating and reading JSON web token
 * <p>
 * M133 BookDB
 *
 * @author Marcel Suter
 * @version 1.0
 * @since 2019-10-14
 */

public class TokenHandler {

    /**
     * builds the token
     *
     * @param data     the token data
     * @param duration the duration of this token in minutes
     * @param role     the user role ("user" or "admin")
     * @return JSON web token as String
     */
    private static String buildToken(String data, int duration, String role) {
        byte[] keyBytes = Config.getProperty("jwtSecret").getBytes(StandardCharsets.UTF_8);
        SecretKey secretKey = Keys.hmacShaKeyFor(keyBytes);
        Date now = new Date();
        Date expiration = new Date(now.getTime() + duration * 60000);
        return Jwts.builder()
                .setIssuer("BookDAO")
                .setSubject(encrypt(data, getJwtEncrypt()))
                .claim("role", encrypt(role, getJwtEncrypt()))
                .setExpiration(expiration)
                .setIssuedAt(now)
                .signWith(secretKey)
                .compact();
    }

    /**
     * reads all claims from the token
     *
     * @param token JSON web token
     * @return claims as a map
     */
    public static Map<String, String> readClaims(String token) {
        Map<String, String> claimMap = new HashMap<>();
        Jws<Claims> jwsClaims;
        byte[] keyBytes = Config.getProperty("jwtSecret").getBytes(StandardCharsets.UTF_8);
        SecretKey secretKey = Keys.hmacShaKeyFor(keyBytes);
        try {
            jwsClaims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
            claimMap.put(
                    "subject",
                    decrypt(
                            jwsClaims.getBody().getSubject(),
                            getJwtEncrypt()
                    )
            );


        } catch (JwtException ex) {
            ex.printStackTrace();
            System.out.println(ex.getCause());
        }
        return claimMap;
    }

    /**
     * build a cookie with a jwtoken
     *
     * @param tokenData the data to be stored in the cookie
     * @return NewCookie
     */
    public static NewCookie buildCookie(String tokenData) {
        NewCookie newCookie;
        if (tokenData == null) {
            newCookie = new NewCookie(
                    "jwtoken",
                    "",
                    "/",
                    Config.getProperty("cookieDomain"),
                    "Auth-Token",
                    60,
                    Boolean.parseBoolean(Config.getProperty("cookieSecure")),
                    false
            );
        } else {
            String token = buildToken(
                    tokenData,
                    10,
                    "admin");

            newCookie = new NewCookie(
                    "jwtoken",
                    token,
                    "/",
                    Config.getProperty("cookieDomain"),
                    "Auth-Token",
                    6000,
                    Boolean.parseBoolean(Config.getProperty("cookieSecure")),
                    false
            );
        }

        return newCookie;
    }

    /**
     * encrypts the string
     *
     * @param strToEncrypt string to be encrypted
     * @return encrypted string
     * @author Lokesh Gupta (https://howtodoinjava.com/security/java-aes-encryption-example/)
     */
    private static String encrypt(String strToEncrypt, String secret) {
        try {
            SecretKey secretKey = new SecretKeySpec(secret.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(
                    cipher.doFinal(
                            strToEncrypt.getBytes(StandardCharsets.UTF_8)
                    )
            );
        } catch (Exception ex) {
            System.out.println("Error while encrypting: " + ex.toString());
        }
        return null;
    }

    /**
     * decrypts the string
     *
     * @param strToDecrypt string to be dencrypted
     * @return decrypted string
     * @author Lokesh Gupta (https://howtodoinjava.com/security/java-aes-encryption-example/)
     */
    private static String decrypt(String strToDecrypt, String secret) {
        try {
            SecretKey secretKey = new SecretKeySpec(secret.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception ex) {
            System.out.println("Error while decrypting: " + ex.toString());
        }
        return null;
    }

    /**
     * gets the jwtkey from the propierties
     *
     * @return the jwtKey
     */
    private static String getJwtEncrypt() {
        String rawKey = Config.getProperty("jwtKey");
        int multi8 = rawKey.length() / 8;
        return rawKey.substring(0, (multi8 * 8));
    }
}
