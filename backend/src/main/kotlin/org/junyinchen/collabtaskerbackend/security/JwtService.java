package org.junyinchen.collabtaskerbackend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Service;

import java.util.function.Function;

import javax.crypto.SecretKey;

@Service
public class JwtService {

    private static final String SECRET_KEY =
            "37d380797e1fade63c4147fc6ee821ba9b992554558752b9aa274eae46a89245";

    public String extractUsername(String token) {
        return null;
    }

    public <T> T extractCliam(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
