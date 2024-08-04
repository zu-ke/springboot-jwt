package com.zuke.springbootjwt;

import io.jsonwebtoken.*;

import java.util.Date;
import java.util.UUID;

/**
 * @author zukedog@163.com
 * @date 2024/8/4 13:14
 */
public class Test {

    private long validTime = 1000*60*60; //一个小时
    private String signature = "zuke";

    @org.junit.jupiter.api.Test
    public void jwt() {
        JwtBuilder jwtBuilder = Jwts.builder();
        String token = jwtBuilder
                //Header
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                //Payload
                //1.用户信息
                .claim("username", "root")
                .claim("role", "admin")
                //2.(有效时间)
                .setExpiration(new Date(System.currentTimeMillis() + validTime))
                //3.jwt的id
                .setId(UUID.randomUUID().toString())
                //signature
                .signWith(SignatureAlgorithm.HS256, signature)
                .compact();
        System.out.println(token);
    }

    @org.junit.jupiter.api.Test
    public void parseJwt() {
        //上面生成的token
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6InJvb3QiLCJyb2xlIjoiYWRtaW4iLCJzdWIiOiJhZG1pbi10ZXN0IiwiZXhwIjoxNzIyNzUyNjY4LCJqdGkiOiIzNzdjNWI3YS1kOWQzLTRmZDctOTM1Ni0zZTYzMmNlNjM4YzgifQ.1Nj9CxxgQHH3sTD8NEq5-sqzK3CrCLkB3p2hsAhiyZg";
        JwtParser jwtParser = Jwts.parser();
        Jws<Claims> claimsJws = jwtParser.setSigningKey(signature).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        System.out.println(claims.get("username"));
        System.out.println(claims.get("role"));
        System.out.println(claims.getId()); //id
        System.out.println(claims.getSubject()); //主题
        System.out.println(claims.getExpiration()); //过期截至时间
    }
}
