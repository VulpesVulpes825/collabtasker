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

    public AuthenticationResponse refresh(UserDetails user) {
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
