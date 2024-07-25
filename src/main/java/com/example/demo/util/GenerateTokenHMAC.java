package com.example.demo.util;

import com.example.demo.exception.IllegalArgumentInMyFunctionException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class GenerateTokenHMAC {
    @Value("${application.security.jwt.secret-key}")
    private String SECRET_KEY;
    @Value("${token.user.id}")
    private String TOKEN_USER_ID;
    @Value("${token.user.role}")
    private String TOKEN_USER_ROLE;
    private final static Integer EXPIRATION_TOKEN = 7;
    /**
     * create string token signature by HMAC key
     * @param idUser user's id
     * @param role user's role
     * @return string token
     */
    public String createTokenHMAC(String idUser,String role){
        if(idUser.isBlank()){
            throw new IllegalArgumentInMyFunctionException();
        }
        Instant now = Instant.now();
        String jwtToken = Jwts.builder()
                .claim(TOKEN_USER_ID,idUser)
                .claim(TOKEN_USER_ROLE,role)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(EXPIRATION_TOKEN, ChronoUnit.DAYS)))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
        return jwtToken;
    }

    private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * decode token
     * @param jwtString string token
     * @return
     */
    public Jws<Claims> parseJwt(String jwtString){
        Jws<Claims> jwt = Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(jwtString);
        return jwt;
    }
}
