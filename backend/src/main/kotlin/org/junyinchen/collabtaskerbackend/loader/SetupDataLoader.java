package org.junyinchen.collabtaskerbackend.loader;

import jakarta.transaction.Transactional;

import lombok.extern.slf4j.Slf4j;

import org.jetbrains.annotations.NotNull;
import org.junyinchen.collabtaskerbackend.models.Privilege;
import org.junyinchen.collabtaskerbackend.models.Role;
import org.junyinchen.collabtaskerbackend.models.User;
import org.junyinchen.collabtaskerbackend.repositories.PrivilegeRepository;
import org.junyinchen.collabtaskerbackend.repositories.RoleRepository;
import org.junyinchen.collabtaskerbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

@Component
@Slf4j
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;
    private final PasswordEncoder passwordEncoder;
    boolean alreadySetup = false;

    @Autowired
    public SetupDataLoader(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PrivilegeRepository privilegeRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.privilegeRepository = privilegeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void onApplicationEvent(@NotNull ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }
        log.info("Populate Database with Roles");
        Role userRole = createRoleIfNotFound("ROLE_USER", Collections.emptyList());
        var user =
                User.builder()
                        .username("test")
                        .firstName("Test")
                        .lastName("Test")
                        .password(passwordEncoder.encode("test"))
                        .roles(Collections.singletonList(userRole))
                        .enabled(true)
                        .build();
        userRepository.save(user);
    }

    @Transactional
    Role createRoleIfNotFound(String name, Collection<Privilege> privileges) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }
}
