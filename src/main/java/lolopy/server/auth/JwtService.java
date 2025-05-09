package lolopy.server.auth;

import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    public static final String SECRET = "5rWBcjsnkL6v9ZvjXS2BaDz7B9TESG6BAFW0GmKxY1n9OuCuFZarx4JQw8jVRov/TFMhxDpfSICFrDKvCIcmsg==";

    private static final long VALIDITY = TimeUnit.MINUTES.toMillis(60);
    private static final long REFRESH_TOKEN_EXPIRATION = TimeUnit.DAYS.toMillis(7);

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(generateSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String generateToken(CustomUserDetails userDetails) {
        Map<String, String> claims = new HashMap<>();
        claims.put("iss", "http://secure.genuiecoder.com");
        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getEmail())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusMillis(VALIDITY)))
                .signWith(generateSecretKey()).compact();
    }

    public String generateRefreshToken(CustomUserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .signWith(generateSecretKey())
                .compact();

    }

    public String extractUserEmail(String jwt) {
        Claims claims = Jwts.parser()
                .verifyWith(generateSecretKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();

        return claims.getSubject();
    }

    public boolean isTokenValid(String jwt) {
        Claims claims = getClaims(jwt);
        return claims.getExpiration().after(Date.from(Instant.now()));
    }

    private SecretKey generateSecretKey() {
        byte[] decodedKey = Base64.getDecoder().decode(SECRET);
        return Keys.hmacShaKeyFor(decodedKey);
    }
}
