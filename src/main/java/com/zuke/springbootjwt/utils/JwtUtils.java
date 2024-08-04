package com.zuke.springbootjwt.utils;

import io.jsonwebtoken.*;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.UUID;

/**
 * @author zukedog@163.com
 * @date 2024/8/4 13:30
 */
public class JwtUtils {
    /**
     * 生成JWT
     * @param username 用户名
     * @param role 用户角色
     * @param validTime JWT有效期（毫秒）
     * @param signature 签名密钥
     * @return 生成的JWT字符串
     */
    public static String generateToken(String username, String role, long validTime, String signature) {
        JwtBuilder jwtBuilder = Jwts.builder();
        String token = jwtBuilder
                // Header
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                // Payload
                .claim("username", username)
                .claim("role", role)
                .setExpiration(new Date(System.currentTimeMillis() + validTime))
                .setId(UUID.randomUUID().toString())
                // Signature
                .signWith(SignatureAlgorithm.HS256, signature)
                .compact();
        return token;
    }

    /**
     * 校验并解析JWT
     * @param token JWT字符串
     * @param signature 签名密钥
     * @return 解析后的Claims对象，如果校验失败则返回null
     */
    public static boolean checkToken(String token, String signature) {
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(signature)) {
            return false;
        }
        try {
            Jwts.parser()
                    .setSigningKey(signature)
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            // JWT校验失败
            e.printStackTrace();
            return false;
        }
    }

}
