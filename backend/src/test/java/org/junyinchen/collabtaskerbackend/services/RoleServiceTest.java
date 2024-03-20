package org.junyinchen.collabtaskerbackend.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junyinchen.collabtaskerbackend.loader.SetupDataLoader;
import org.junyinchen.collabtaskerbackend.models.Role;
import org.junyinchen.collabtaskerbackend.repositories.RoleRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RoleServiceTest {
    @MockBean SetupDataLoader setupDataLoader;
    @Mock RoleRepository roleRepository;
    @InjectMocks RoleServiceImpl roleService;
    Role role;

    @BeforeEach
    public void setup() {
        role = Role.builder().name("test").build();
    }

    @Test
    void testSaveRole() {
        when(roleRepository.save(any(Role.class))).thenReturn(role);
        Role savedRole = roleService.saveRole(role);
        assertEquals(role.getName(), savedRole.getName());
        verify(roleRepository).save(any(Role.class));
    }
}
