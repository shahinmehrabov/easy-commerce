package com.easycommerce.auth.jwt;

import com.easycommerce.config.AppConstants;
import com.easycommerce.user.UserDetailsImpl;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {

    @Value("${jwt.expiration.ms}")
    private int tokenExpirationMs;

    @Value("${jwt.cookie.name}")
    private String cookieName;
    private SecretKey secretKey;

    public String getTokenFromCookie(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, cookieName);

        if (cookie != null)
            return cookie.getValue();
        else
            return null;
    }

    public ResponseCookie generateCookie(UserDetailsImpl userDetails) {
        String token = generateTokenFromUsername(userDetails.getUsername());

        return ResponseCookie.from(cookieName, token)
                .path("/api")
                .maxAge(24 * 60 * 60)
                .httpOnly(false)
                .build();
    }

    public ResponseCookie getCleanCookie() {
        return ResponseCookie.from(cookieName, null)
                .path("/api")
                .build();
    }

    public String getTokenFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith(AppConstants.TOKEN_PREFIX))
            return bearerToken.substring(7);

        return null;
    }

    public String generateTokenFromUsername(String username) {
        return Jwts
                .builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + tokenExpirationMs))
                .signWith(getSecretKey())
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts
                .parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    private SecretKey getSecretKey() {
        if (secretKey == null)
            secretKey = Jwts.SIG.HS256.key().build();

        return secretKey;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) getSecretKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
