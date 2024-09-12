package com.harmlessprince.todomanagerapplication.utils;

import com.harmlessprince.todomanagerapplication.models.User;
import io.jsonwebtoken.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JWTHelper {
    private static final Logger logger = LoggerFactory.getLogger(JWTHelper.class);

    @Value("${harmlessprince.app.jwtSecret}")
    private String jwtSecret;

    @Value("${harmlessprince.app.jwtCookieName}")
    private String jwtCookieName;
    @Value("${harmlessprince.app.jwtExpiration}")
    private String jwtExpiration;

    public ResponseCookie generateJwtCookie(User user) {

        String jwt = generateTokenFromUsername(user.getEmail());
        logger.info("Jwt IS BEEN GENERATED generateJwtCookie: {}", jwt);
        return ResponseCookie.from(jwtCookieName, jwt)
                .path("/api")
                .maxAge(24 * 60 * 60)
                .build();
    }

    public ResponseCookie getCleanJwtCookie() {
        ResponseCookie cookie = ResponseCookie.from(jwtCookieName, null).path("/api").build();
        return cookie;
    }

    public String getUserNameFromJwtToken(String authToken) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJwt(authToken).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJwt(authToken);
            return true;
        } catch (SignatureException exception) {
            logger.error("Invalid JWT signature: {}", exception.getMessage());
        } catch (MalformedJwtException exception) {
            logger.error("Invalid JWT token: {}", exception.getMessage());
        } catch (ExpiredJwtException exception) {
            logger.error("JWT is expired: {}", exception.getMessage());
        } catch (UnsupportedJwtException exception) {
            logger.error("JWT token is not supported : {}", exception.getMessage());
        } catch (IllegalArgumentException exception) {
            logger.error("JWT claims string is empty: {}", exception.getMessage());
        }
        return false;
    }

    public String getJwtFromCookies(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, jwtCookieName);
        if (cookie != null) {
            return cookie.getValue();
        }
        return null;
    }

    public String generateTokenFromUsername(String username) {

        Date currentDate = new Date();
        // Calculate the future date (e.g., 3 days from the current date)
        long futureTimeMillis = (currentDate).getTime() + Long.parseLong(jwtExpiration);
        Date futureDate = new Date(futureTimeMillis);
//        logger.info("TOKEN IS BEEN GENERATED generateTokenFromUsername: {}", futureDate);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(jwtExpiration)))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();


    }
}
