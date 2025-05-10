package com.api.ApiStorage.auth;

import com.api.ApiStorage.Database.entity.User;
import com.api.ApiStorage.Database.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import org.springframework.stereotype.Service;


@Service
public class TokenManager {

    private static final int validity = 30 * 60 * 1000;
    Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final UserRepository userRepository;
    public TokenManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String generateToken(String username) {

        User user = userRepository.findByUsername(username)
                .toJavaUtil().orElseThrow(() -> new RuntimeException("User not found"));

        return Jwts.builder().
                claim("role", user.getRole().getName())
                .setSubject(username)
                .setIssuer("ww.tokencompany.com")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + validity))
                .signWith(key)
                .compact();
    }

    public boolean tokenValidate(String token) {
        if (getUsernameToken(token) != null && isExpired(token)) {
            return true;
        }
        return false;
    }

    public String getUsernameToken(String token) {
        Claims claims = getClaims(token);
        return claims.getSubject();
    }

    public boolean isExpired(String token) {
        Claims claims = getClaims(token);
        return claims.getExpiration().after(new Date(System.currentTimeMillis()));
    }

    Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
    }

}
