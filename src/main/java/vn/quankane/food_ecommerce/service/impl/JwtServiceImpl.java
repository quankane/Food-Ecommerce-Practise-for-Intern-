package vn.quankane.food_ecommerce.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import vn.quankane.food_ecommerce.constant.TokenType;
import vn.quankane.food_ecommerce.exception.InvalidDataException;
import vn.quankane.food_ecommerce.repository.InvalidatedTokenRepository;
import vn.quankane.food_ecommerce.service.JwtService;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "JWT-SERVICE")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.expiryHour}")
    @NonFinal
    long expiryHour;

    @Value("${jwt.expiryDay}")
    @NonFinal
    long expiryDay;

    @Value("${jwt.accessKey}")
    @NonFinal
    String accessKey;

    @Value("${jwt.refreshKey}")
    @NonFinal
    String refreshKey;

    InvalidatedTokenRepository invalidatedTokenRepository;

    @Override
    public String generateAccessToken(String userId, String username, Collection<? extends GrantedAuthority> authorities) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("roles", authorities);

        return generateAccessToken(claims, username);
    }

    @Override
    public String generateRefreshToken(String userId, String username, Collection<? extends GrantedAuthority> authorities) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("roles", authorities);

        return generateRefreshToken(claims, username);
    }

    @Override
    public String extractUsername(String token, TokenType type) {
        return extractClaim(token, type, Claims::getSubject);
    }

    @Override
    public boolean isValid(String token, TokenType type, String name) {
        final String username = extractUsername(token, type);
        String jwtId = extractTokenId(token, type);

        boolean isInvalidated = invalidatedTokenRepository.existsById(jwtId);

        return username.equals(name) && !isTokenExpired(token,type) && !isInvalidated;
    }

    @Override
    public boolean isExpired(String token, TokenType type) {
        return isTokenExpired(token, type);
    }

    private String generateAccessToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * expiryHour))
                .signWith(getKey(TokenType.ACCESS_TOKEN), SignatureAlgorithm.HS256)
                .compact();
    }

    private String generateRefreshToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * expiryDay))
                .signWith(getKey(TokenType.ACCESS_TOKEN), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getKey(TokenType type) {
        switch (type) {
            case ACCESS_TOKEN -> {
                return Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessKey));
            }
            case REFRESH_TOKEN -> {
                return Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshKey));
            }
            default -> throw new InvalidDataException("");
        }
    }

    private<T> T extractClaim(String token, TokenType type, Function<Claims, T> claimsTFunction) {
        final Claims claims = extractAllClaims(token, type);
        return claimsTFunction.apply(claims);
    }

    private Claims extractAllClaims(String token, TokenType type) {
        try {
            return Jwts.parserBuilder().setSigningKey(getKey(TokenType.ACCESS_TOKEN)).build().parseClaimsJws(token).getBody();
        } catch (Exception e) {
            log.error("Extra all claim failed, message = {}", e.getMessage());
            throw new AccessDeniedException("Access denied: "+  e.getMessage());
        }
    }

    private boolean isTokenExpired (String token, TokenType type) {
        return extractTokenExpiration(token, type).before(new Date());
    }

    private Date extractTokenExpiration(String token, TokenType type) {
        return extractClaim(token, type, Claims::getExpiration);
    }

    private String extractTokenId(String token, TokenType type) {
        return extractClaim(token, type, Claims::getId);
    }
}
