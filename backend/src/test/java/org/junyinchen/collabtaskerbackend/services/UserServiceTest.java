package org.junyinchen.collabtaskerbackend.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junyinchen.collabtaskerbackend.exceptions.IncorrectAgeValueException;
import org.junyinchen.collabtaskerbackend.loader.SetupDataLoader;
import org.junyinchen.collabtaskerbackend.models.User;
import org.junyinchen.collabtaskerbackend.repositories.UserRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserServiceTest {
    @MockBean SetupDataLoader setupDataLoader;
    @Mock UserRepository userRepository;
    @InjectMocks UserServiceImpl userService;
    User user;

    @BeforeEach
    public void setup() {
        user = User.builder().username("test").password("test").build();
    }

    @Test
    void testSaveUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        User savedUser = userService.saveUser(user);
        assertEquals(user.getUsername(), savedUser.getUsername());
        verify(userRepository).save(any(User.class));
    }

    @ParameterizedTest
    @ValueSource(ints = {-10, 0, -15, -20})
    void givenAgeLessThan18_ShouldThrowException(int age) {
        assertThrows(
                IncorrectAgeValueException.class,
                () -> {
                    userService.isAdult(age);
                });
    }
}
