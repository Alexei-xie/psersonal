package com.itself.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 */
public class JwtUtils {

    /**
     * 过期时间60分钟
     */
    private static final long EXPIRE_TIME = 60 * 60 * 1000;
    /**
     * 加密密钥
     */
    private static final String KEY = "miyao";

    /**
     * 生成token
     *
     * @param account 用户名
     * @param id      用户id
     */
    public static String createToken(String account, String id) {
        Map<String, Object> header = new HashMap();
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        JwtBuilder builder = Jwts.builder().setHeader(header)
                .setId(id) //setID:用户ID
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME))//setExpiration:token过期时间  当前时间+有效时间
                .setSubject(account) //setSubject:用户名
                .setIssuedAt(new Date()) //setIssuedAt:token创建时间
                .signWith(SignatureAlgorithm.HS256, KEY);    //signWith:加密方式
        return builder.compact();
    }

//得到token中的account
    public static String getAccount(String token) {
        Claims claims = Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }






}

