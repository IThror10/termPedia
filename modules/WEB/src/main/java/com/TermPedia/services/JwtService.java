package com.TermPedia.services;

import com.TermPedia.dto.users.UserPrivateData;
import com.TermPedia.dto.users.UserPublicData;
import com.TermPedia.commands.user.ValidateCommand;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.logging.Logger;

@Service
public class JwtService {
    private static final String SECRET_KEY = "FXJaNdRgUkXp2s5v8x/AzDlG+KbPeShVqQDZqOVaUKAd";

    public ValidateCommand getValidateCommand(String jwt) {
        Logger.getLogger("Auth").warning(jwt);
        final Claims claims = extractAllClaims(jwt);
        return new ValidateCommand(
                claims.getSubject(),
                claims.get("secret", String.class)
        );
    }

    public String generateToken(UserPrivateData user) {
        Map<String, Object> map = new HashMap();
        map.put("secret", user.getSecret());
        return generateToken(map, user);
    }

    public String generateToken(Map<String, Object> extraClaims, UserPublicData user) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(user.getLogin())
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
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
}
