package com.TermPedia.services;

import com.TermPedia.dto.users.UserPublicData;
import com.TermPedia.securityDTO.ExtendedUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.IOException;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.logging.Logger;

@Service
public class JwtService {
    private static final String SECRET_KEY = "FXJaNdRgUkXp2s5v8x/AzDlG+KbPeShVqQDZqOVaUKAd";

    public String extractUsername(String authToken) {
        return  extractClaim(authToken, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String authToken) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(authToken)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] key_bytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(key_bytes);
    }

    public String generateToken(UserPublicData user) {
        Map<String, Object> map = new HashMap();
        map.put("userId", user.userID());
        return generateToken(map, user);
    }

    public String generateToken(Map<String, Object> extraClaims, UserPublicData user) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(user.login())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String authToken, UserDetails user) {
        final String userName = extractUsername(authToken);
        return (userName.equals(user.getUsername()) && !isTokenExpired(authToken));
    }

    private boolean isTokenExpired(String authToken) {
        return extractExpiration(authToken).before(new Date());
    }

    private Date extractExpiration(String authToken) {
        return extractClaim(authToken, Claims::getExpiration);
    }
}
