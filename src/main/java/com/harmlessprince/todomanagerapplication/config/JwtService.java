package com.harmlessprince.todomanagerapplication.config;

import com.harmlessprince.todomanagerapplication.models.User;
import com.harmlessprince.todomanagerapplication.utils.JWTHelper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);
    @Value("${harmlessprince.app.jwtSecret}")
    private String jwtSecret;

    @Value("${harmlessprince.app.jwtCookieName}")
    private String jwtCookieName;
    @Value("${harmlessprince.app.jwtExpiration}")
    private String jwtExpiration;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String generateToken(User userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public boolean validateJwtToken(String authToken, User user) throws java.security.SignatureException {
        try {
            final String username = extractUsername(authToken);

            return (username.equals(user.getEmail()) && !isTokenExpired(authToken));
        } catch (SignatureException exception) {
            throw new java.security.SignatureException("Invalid jwt signature");
        } catch (MalformedJwtException exception) {
            throw new MalformedJwtException("Invalid jwt token");
//            logger.error("Invalid JWT token: {}", exception.getMessage());
        } catch (ExpiredJwtException exception) {
            logger.error("JWT is expired: {}", exception.getMessage());
        } catch (UnsupportedJwtException exception) {
            logger.error("JWT token is not supported : {}", exception.getMessage());
        } catch (IllegalArgumentException exception) {
            logger.error("JWT claims string is empty: {}", exception.getMessage());
        }
        return false;
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String generateToken(Map<String, Object> extraClaims, User userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(jwtExpiration)))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return (Claims) Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parse(token).getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
