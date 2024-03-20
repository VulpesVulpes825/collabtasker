package org.junyinchen.collabtaskerbackend.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junyinchen.collabtaskerbackend.models.User;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class JwtServiceTest {
    final String username = "user";
    User user;
    @InjectMocks private JwtService jwtService;
    @Mock private UserDetails userDetails;
    @Mock private UserService userService;

    @BeforeEach
    public void setup() {
        when(userDetails.getUsername()).thenReturn(username);
        when(userService.getUser(anyString()))
                .thenReturn(Optional.ofNullable(User.builder().username(username).build()));
    }

    @Test
    void testGenerateToken() {
        String token = jwtService.generateToken(userDetails);
        assertNotNull(token);
    }
}
