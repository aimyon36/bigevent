package com.flyme.bigenvent;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@Slf4j
public class JwtTest {
    @Test
    public void testGen(){
        HashMap<String, Object> clains = new HashMap<>();
        clains.put("id",1);
        clains.put("username","root");
//        生成jwt
        String token = JWT.create()
//                添加一个声明
                .withClaim("user", clains)
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 12))
                .sign(Algorithm.HMAC256("aimyon"));
        log.info("{}",token);
    }
    @Test
    public void testParse(){
        String token="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" +
                ".eyJ1c2VyIjp7ImlkIjoxLCJ1c2VybmFtZSI6InJvb3QifSwiZXhwIjoxNjk5NTQ0NzYxfQ." +
                "o6ytMyTljYTOOxGZ8Mx0aWs9z9enB0AQJEuf6esv-7U";
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("aimyon")).build();
        DecodedJWT verify = jwtVerifier.verify(token);//生成jwt对象
//        Claim us = verify.getClaim("us");
        Map<String, Claim> claims = verify.getClaims();
        Claim user = claims.get("user");
        log.info("{}",user);

    }
}
