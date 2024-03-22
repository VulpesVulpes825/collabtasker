package org.junyinchen.collabtaskerbackend.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import lombok.RequiredArgsConstructor;

import org.junyinchen.collabtaskerbackend.models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

/**
 * Utility class for handling JWT (JSON Web Token) related operations including token generation,
 * validation, and extracting claims such as username and expiration time. It uses {@link
 * UserService} to fetch user details during token generation for additional claims.
 */
@Service
@RequiredArgsConstructor
public class JwtService {

    private static final String SECRET_KEY = "K/3/O7YQ86fQSHSskgvWDOE559FYMcgOhKk/kogeLjs=";

    private final UserService userService;

    /**
     * Extracts the username from the JWT token.
     *
     * @param token the JWT token
     * @return the username extracted from the token
     */
    public String extractUsername(String token) {
        return extractCliam(token, Claims::getSubject);
    }

    /**
     * Generic method to extract specific claims from the JWT token using a claims resolver
     * function.
     *
     * @param <T> the type of the claim being extracted
     * @param token the JWT token
     * @param claimsResolver a function to resolve the claim from the token
     * @return the extracted claim
     */
    public <T> T extractCliam(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Generates a JWT token for the given user details.
     *
     * @param userDetails the user details to generate the token for
     * @return the generated JWT token
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Validates the JWT token. Checks if the token is valid by comparing the username and checking
     * if the token has expired.
     *
     * @param token the JWT token to validate
     * @param userDetails the user details for validation against the token
     * @return true if the token is valid, false otherwise
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Checks if the JWT token has expired.
     *
     * @param token the JWT token to check
     * @return true if the token has expired, false otherwise
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date of the JWT token.
     *
     * @param token the JWT token
     * @return the expiration date of the token
     */
    private Date extractExpiration(String token) {
        return extractCliam(token, Claims::getExpiration);
    }

    /**
     * Generates a JWT token for the given user details with additional claims.
     *
     * @param extraClaims extra claims to include in the JWT token
     * @param userDetails the user details to generate the token for
     * @return the generated JWT token
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        User user = userService.getUser(userDetails.getUsername()).orElseThrow();
        extraClaims.put("given_name", user.getFirstName());
        extraClaims.put("family_name", user.getLastName());
        return Jwts.builder()
                .claims()
                .empty()
                .add(extraClaims)
                .and()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(getSignInKey(), Jwts.SIG.HS256)
                .compact();
    }

    /**
     * Extracts all claims from the JWT token.
     *
     * @param token the JWT token
     * @return the claims extracted from the token
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Retrieves the signing key for the JWT token from the SECRET_KEY.
     *
     * @return the signing key
     */
    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
