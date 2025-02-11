package com.kolown.porring.security.jwt;

import com.kolown.porring.security.repository.EmailAccountRepository;
import com.kolown.porring.security.repository.OauthAccountRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.persistence.DiscriminatorValue;
import java.security.Key;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expirationTime;

    private final EmailAccountRepository emailAccountRepository;
    private final OauthAccountRepository oauthAccountRepository;

    public String generateToken(Authentication authentication) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTime);

        Object principal = authentication.getPrincipal();
        String subType = principal.getClass().getAnnotation(DiscriminatorValue.class).value();

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("sub_type", subType)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = parseClaims(token);
        String username = claims.getSubject();
        String subType = claims.get("sub_type", String.class);

        UserDetails principal;
        if (subType.equals("OAUTH")) {
            principal = oauthAccountRepository.findByOauthNumber(username)
                    .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        } else {
            principal = emailAccountRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        }

        return new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException e) {
            throw new RuntimeException("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            throw new RuntimeException("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("JWT claims string is empty.", e);

        }
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
}
