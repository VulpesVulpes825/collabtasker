package org.junyinchen.collabtaskerbackend.loader;

import jakarta.transaction.Transactional;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import org.junyinchen.collabtaskerbackend.models.Role;
import org.junyinchen.collabtaskerbackend.models.TodoBoard;
import org.junyinchen.collabtaskerbackend.models.TodoItem;
import org.junyinchen.collabtaskerbackend.models.User;
import org.junyinchen.collabtaskerbackend.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Component responsible for initializing the application with default data upon startup. It listens
 * for {@link ContextRefreshedEvent} to ensure that the setup is only performed once after the
 * application context is fully refreshed. This class sets up default roles, privileges, users,
 * boards, and items in the application's database.
 */
@Component
@Slf4j
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;
    private final PasswordEncoder passwordEncoder;
    private final BoardRepository boardRepository;
    private final ItemRepository itemRepository;
    boolean alreadySetup = false;

    /**
     * Constructs a SetupDataLoader with the specified repositories and password encoder.
     *
     * @param userRepository the user repository for accessing user data
     * @param roleRepository the role repository for accessing role data
     * @param privilegeRepository the privilege repository for accessing privilege data
     * @param passwordEncoder the password encoder for encoding passwords
     * @param boardRepository the board repository for accessing board data
     * @param itemRepository the item repository for accessing item data
     */
    @Autowired
    public SetupDataLoader(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PrivilegeRepository privilegeRepository,
            PasswordEncoder passwordEncoder,
            BoardRepository boardRepository,
            ItemRepository itemRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.privilegeRepository = privilegeRepository;
        this.passwordEncoder = passwordEncoder;
        this.boardRepository = boardRepository;
        this.itemRepository = itemRepository;
    }

    /**
     * Handles the {@link ContextRefreshedEvent}. It populates the database with initial data
     * including roles, users, boards, and items if the setup has not already been done.
     *
     * @param event the context refreshed event that triggers the data loading
     */
    @Override
    @Transactional
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
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
        log.info("Populate Database with Todo Board");
        var board = TodoBoard.builder().title("Test's Todo Board").role(boardRole).build();
        boardRepository.save(board);
        log.info("Populate Database with Todo Item");
        var item =
                TodoItem.builder()
                        .title("A sample Todo Item")
                        .content("More details of the Todo Item")
                        .board(board)
                        .build();
        itemRepository.save(item);
        log.info("Link Todo Item with Todo Board");
        board.setItems(Collections.singletonList(item));
        log.info("Populate Database with User");
        var user =
                User.builder()
                        .username("test")
                        .firstName("Test")
                        .lastName("Test")
                        .password(passwordEncoder.encode("test"))
                        .roles(List.of(userRole, boardRole))
                        .ownedBoard(Collections.singletonList(board))
                        .enabled(true)
                        .build();
        board.setOwner(user);
        userRepository.save(user);
        alreadySetup = true;
    }
}
