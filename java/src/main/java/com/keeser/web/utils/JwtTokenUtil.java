package com.keeser.web.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ClaimsBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

// import org.springframework.security.core.userdetails.UserDetails;  todo: 添加security支持
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import org.springframework.stereotype.Component;
import java.util.*;
import javax.crypto.SecretKey;


@Component  // 代表spring组件
public class JwtTokenUtil {
    // 私钥
    private final String secret = "3MnJb57tW9TAvkYFQEDOgLdSRuCzmXcS";
    private final long expiration = 24 * 60 * 60;
    // 加密算法
    private final SecureDigestAlgorithm<SecretKey, SecretKey> ALGORITHM = Jwts.SIG.HS256;
    // 秘钥实例
    private final SecretKey KEY = Keys.hmacShaKeyFor(secret.getBytes());

    // 生成令牌
    public String generateToken(String user_name){
        ClaimsBuilder claims_builder = Jwts.claims();
        claims_builder.add("userName", user_name);
        return generateJWT(claims_builder);
    }

    // 验证令牌
    public Boolean checkToken(String token, String username){
        try{
            return parseJWT(token).get("userName") == username
                    && !isExpired(token);
        }catch (Exception e){
            return false;
        }
    }

    // 获取token中的用户名
    public String getUserName(String token){
        try{
            return (String) parseJWT(token).get("userName");
        }catch (Exception e){
            return null;
        }
    }

    // token是否失效
    // todo 判断密码修改就失效, 通过更改userDetails.model.tokenInvalidDate实现
    private Boolean isInvalid(String token, Date tokenInvalidDate){
        try {
            Claims claims = parseJWT(token);
            return claims.getIssuedAt().before(tokenInvalidDate) && !isExpired(token);
        }catch (NullPointerException e){
            return false;
        }
    }

    // 令牌是否过期
    private Boolean isExpired(String token){
         try {
             return parseJWT(token).getExpiration().before(new Date());
         }catch (NullPointerException e){
             return false;
         }
    }

    // 生成秘钥
    private String generateJWT(ClaimsBuilder claims_builder){
        try {
            // 令牌id
            String uuid = UUID.randomUUID().toString();
            return Jwts.builder()
                    // 设置头部信息header
                    .header()
                    .add("typ", "JWT")
                    .add("alg", "HS256")
                    .and()
                    .claims(claims_builder.build())
                    .id(uuid)
                    .issuedAt(new Date())
                    .issuer("auth0")
                    .expiration(new Date(System.currentTimeMillis() + expiration * 1000))
                    .signWith(KEY, ALGORITHM)
                    .compact();
        }
        catch (Exception e){
            return "";
        }
    }

    // 解析秘钥
    private Claims parseJWT(String token){
        try{
            return Jwts.parser()
                    .verifyWith(KEY)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        }catch (Exception e){
            return null;
        }
    }

}
