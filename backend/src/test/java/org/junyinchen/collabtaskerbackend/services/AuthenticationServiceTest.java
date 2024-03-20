package org.junyinchen.collabtaskerbackend.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junyinchen.collabtaskerbackend.models.AuthenticationRequest;
import org.junyinchen.collabtaskerbackend.models.AuthenticationResponse;
import org.junyinchen.collabtaskerbackend.models.User;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthenticationServiceTest {
    private final String jwtToken = "dummy-jwt-token";
    @Mock private AuthenticationManager authenticationManager;
    @Mock private UserService userService;
    @Mock private JwtService jwtService;
    @InjectMocks private AuthenticationService authenticationService;
    private AuthenticationRequest authenticationRequest;
    private User user;

    @BeforeEach
    public void setup() {
        authenticationRequest = new AuthenticationRequest("user", "password");
        user = User.builder().username("user").password("password").build();
    }

    @Test
    void testAuthenticate() {
        when(userService.getUser(any(String.class))).thenReturn(Optional.of(user));
        when(jwtService.generateToken(any(UserDetails.class))).thenReturn(jwtToken);

        AuthenticationResponse response = authenticationService.authenticate(authenticationRequest);

        assertEquals(jwtToken, response.getToken());
        verify(authenticationManager)
                .authenticate(new UsernamePasswordAuthenticationToken("user", "password"));
        verify(userService).getUser("user");
        verify(jwtService).generateToken(any(UserDetails.class));
    }
}
