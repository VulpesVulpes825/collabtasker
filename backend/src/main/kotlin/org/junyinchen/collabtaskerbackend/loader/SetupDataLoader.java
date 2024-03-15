package org.junyinchen.collabtaskerbackend.loader;

import jakarta.transaction.Transactional;

import lombok.extern.slf4j.Slf4j;

import org.jetbrains.annotations.NotNull;
import org.junyinchen.collabtaskerbackend.models.Role;
import org.junyinchen.collabtaskerbackend.models.TodoBoard;
import org.junyinchen.collabtaskerbackend.models.User;
import org.junyinchen.collabtaskerbackend.repositories.BoardRepository;
import org.junyinchen.collabtaskerbackend.repositories.PrivilegeRepository;
import org.junyinchen.collabtaskerbackend.repositories.RoleRepository;
import org.junyinchen.collabtaskerbackend.repositories.UserRepository;
import org.junyinchen.collabtaskerbackend.services.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;
    private final PasswordEncoder passwordEncoder;
    private final BoardRepository boardRepository;
    private final BoardService boardService;
    boolean alreadySetup = false;

    @Autowired
    public SetupDataLoader(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PrivilegeRepository privilegeRepository,
            PasswordEncoder passwordEncoder,
            BoardRepository boardRepository,
            BoardService boardService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.privilegeRepository = privilegeRepository;
        this.passwordEncoder = passwordEncoder;
        this.boardRepository = boardRepository;
        this.boardService = boardService;
    }

    @Override
    @Transactional
    public void onApplicationEvent(@NotNull ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }
        log.info("Populate Database with Role");
        Role userRole =
                Role.builder().name("ROLE_USER").privileges(Collections.emptyList()).build();
        Role boardRole =
                Role.builder().name("ROLE_BOARD_1").privileges(Collections.emptyList()).build();
        roleRepository.save(userRole);
        roleRepository.save(boardRole);
        log.info("Populate Database with Board");
        var board = TodoBoard.builder().title("Test's Todo Board").role(boardRole).build();
        boardRepository.save(board);
        log.info("Populate Database with User");
        var user =
                User.builder()
                        .username("test")
                        .firstName("Test")
                        .lastName("Test")
                        .password(passwordEncoder.encode("test"))
                        .roles(List.of(userRole, boardRole))
                        .enabled(true)
                        .build();
        userRepository.save(user);
    }
}
