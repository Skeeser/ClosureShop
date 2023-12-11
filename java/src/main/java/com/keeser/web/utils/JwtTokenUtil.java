package com.keeser.web.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Value;
// import org.springframework.security.core.userdetails.UserDetails;  todo: 添加security支持
import org.springframework.stereotype.Component;
import java.util.*;

@Component  // 代表spring组件
public class JwtTokenUtil {
    // 私钥
    private String secret = "3MnJb57tW9TAvkYFQEDOgLdSRuCzmXcS";

    // 生成令牌
    public String generateToken(String user_name){
        Claims claims = Jwts.claims();
        claims.put("userName", user_name);
        return generateJWT(claims);
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
    private String generateJWT(Claims claims){
        long expiration = 24 * 60 * 60;
        return Jwts.builder()
                .setIssuer("auth0")
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    // 解析秘钥
    private Claims parseJWT(String token){
        try{
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
            return null;
        }
    }

}
