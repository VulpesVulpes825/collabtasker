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

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService service;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refresh() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("User {} is trying to refresh token", username);
        User user = userService.getUser(username).orElseThrow();
        return ResponseEntity.ok(service.refresh(user));
    }
}
