package org.junyinchen.collabtaskerbackend.services;

import org.junyinchen.collabtaskerbackend.exceptions.IncorrectAgeValueException;
import org.junyinchen.collabtaskerbackend.models.Role;
import org.junyinchen.collabtaskerbackend.models.TodoBoard;
import org.junyinchen.collabtaskerbackend.models.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);

    Role saveRole(Role role);

    void addRoleToUser(String username, String roleName);

    Optional<User> getUser(String username);

    Collection<TodoBoard> getOwnedBoards(String username);

    Collection<TodoBoard> getMemberBoards(String username);

    Collection<TodoBoard> getAllBoards(String username);

    List<User> getUsers();

    boolean isAdult(int age) throws IncorrectAgeValueException;
}
