package org.junyinchen.collabtaskerbackend.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.junyinchen.collabtaskerbackend.models.AuthenticationRequest;
import org.junyinchen.collabtaskerbackend.models.AuthenticationResponse;
import org.junyinchen.collabtaskerbackend.models.RegisterRequest;
import org.junyinchen.collabtaskerbackend.models.User;
import org.junyinchen.collabtaskerbackend.services.AuthenticationService;
import org.junyinchen.collabtaskerbackend.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling authentication requests such as registration, authentication, and token
 * refresh. It utilizes {@link AuthenticationService} for the core authentication logic and {@link
 * UserService} for user-related operations.
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService service;
    private final UserService userService;

    /**
     * Registers a new user with the provided registration details.
     *
     * @param request the registration request containing user details
     * @return a {@link ResponseEntity} containing the authentication response with status OK
     */
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    /**
     * Authenticates a user based on the provided authentication request details.
     *
     * @param request the authentication request containing login credentials
     * @return a {@link ResponseEntity} containing the authentication response with status OK
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    /**
     * Refreshes the authentication token for the current user. The current user is determined from
     * the security context.
     *
     * @return a {@link ResponseEntity} containing the new authentication response with status OK
     */
    @GetMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refresh() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("User {} is trying to refresh token", username);
        User user = userService.getUser(username).orElseThrow();
        return ResponseEntity.ok(service.refresh(user));
    }
}
