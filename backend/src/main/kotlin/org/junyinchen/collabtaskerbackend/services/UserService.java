package org.junyinchen.collabtaskerbackend.services;

import org.junyinchen.collabtaskerbackend.models.Role;
import org.junyinchen.collabtaskerbackend.models.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);

    Role saveRole(Role role);

    void addRoleToUser(String username, String roleName);

    User getUser(String username);

    List<User> getUsers();
}
