package org.junyinchen.collabtaskerbackend.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.junyinchen.collabtaskerbackend.models.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Service class for handling authentication-related functionalities including user registration,
 * authentication, and JWT token refreshment. It leverages {@link UserService}, {@link
 * PasswordEncoder}, {@link JwtService}, {@link AuthenticationManager}, {@link RoleService}, and
 * {@link BoardService} to perform its duties.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleService roleService;
    private final BoardService boardService;

    /**
     * Registers a new user with the provided registration request details, assigns them a default
     * role, creates a personal board for them, and generates a JWT token for immediate
     * authentication.
     *
     * @param request the registration request containing user details
     * @return an {@link AuthenticationResponse} with the generated JWT token
     */
    public AuthenticationResponse register(RegisterRequest request) {
        log.info("Register user {}", request.getUsername());
        var user =
                User.builder()
                        .username(request.getUsername())
                        .firstName(request.getFirstName())
                        .lastName(request.getLastName())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .roles(Collections.singletonList(roleService.getRole("ROLE_USER")))
                        .build();
        userService.saveUser(user);
        TodoBoard board = TodoBoard.builder().title(user.getUsername() + "'s board").build();
        boardService.saveBoard(board);
        Role role =
                Role.builder()
                        .name("ROLE_BOARD_" + board.getId())
                        .privileges(Collections.emptyList())
                        .build();
        roleService.saveRole(role);
        board.setRole(role);
        userService.addRoleToUser(user.getUsername(), role.getName());
        boardService.saveBoard(board);
        var jwtToken = jwtService.generateToken(user);
        log.info("user {} is registered", request.getUsername());
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    /**
     * Authenticates a user with the provided authentication request details. It checks the
     * credentials and generates a new JWT token if authentication is successful.
     *
     * @param request the authentication request containing login credentials
     * @return an {@link AuthenticationResponse} with the newly generated JWT token
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        log.info("Authenticating user {}", request.getUsername());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword()));
        var user =
                userService
                        .getUser(request.getUsername())
                        .orElseThrow(); // TODO: Implement Exception
        return refresh(user);
    }

    /**
     * Refreshes the JWT token for a given user. This can be used to generate a new token for
     * continuous authentication without requiring the user to log in again.
     *
     * @param user the user details for whom the JWT token needs to be refreshed
     * @return an {@link AuthenticationResponse} with the newly generated JWT token
     */
    public AuthenticationResponse refresh(UserDetails user) {
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
