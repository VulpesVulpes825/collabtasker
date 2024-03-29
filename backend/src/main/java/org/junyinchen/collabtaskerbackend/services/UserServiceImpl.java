package org.junyinchen.collabtaskerbackend.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.junyinchen.collabtaskerbackend.exceptions.IncorrectAgeValueException;
import org.junyinchen.collabtaskerbackend.models.Role;
import org.junyinchen.collabtaskerbackend.models.TodoBoard;
import org.junyinchen.collabtaskerbackend.models.User;
import org.junyinchen.collabtaskerbackend.repositories.RoleRepository;
import org.junyinchen.collabtaskerbackend.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public User saveUser(User user) {
        log.info("Saving new user {} to the database", user.getUsername());
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {}", roleName, username);
        User user = userRepository.findByUsername(username).orElseThrow();
        Role role = roleRepository.findByName(roleName);
        ArrayList<Role> roles = new ArrayList<>(user.getRoles());
        roles.add(role);
        user.setRoles(roles);
    }

    @Override
    public Optional<User> getUser(String username) {
        log.info("Fetching user {}", username);
        return userRepository.findByUsername(username);
    }

    @Override
    public Collection<TodoBoard> getOwnedBoards(String username) {
        log.info("Fetching all boards owned by user {}", username);
        User user = userRepository.findByUsername(username).orElseThrow();
        return user.getOwnedBoard();
    }

    @Override
    public Collection<TodoBoard> getMemberBoards(String username) {
        log.info("Fetching all boards that user {} is a member", username);
        User user = userRepository.findByUsername(username).orElseThrow();
        return user.getBoards();
    }

    @Override
    public Collection<TodoBoard> getAllBoards(String username) {
        log.info("Fetching all boards that user {} is a part of", username);
        return Stream.concat(getOwnedBoards(username).stream(), getMemberBoards(username).stream())
                .toList();
    }

    @Override
    public List<User> getUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    @Override
    public boolean isAdult(int age) throws IncorrectAgeValueException {
        if (age <= 0) {
            throw new IncorrectAgeValueException("Age should not be zero or negative");
        }
        return age >= 18;
    }
}
