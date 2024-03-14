package org.junyinchen.collabtaskerbackend.controllers;

import lombok.RequiredArgsConstructor;

import org.junyinchen.collabtaskerbackend.models.AuthenticationRequest;
import org.junyinchen.collabtaskerbackend.models.AuthenticationResponse;
import org.junyinchen.collabtaskerbackend.models.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    // @PostMapping("/register")
    // public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request)
    // {}

    // @PostMapping("/authenticate")
    // public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest
    // request) {}
}
