package org.junyinchen.collabtaskerbackend.services;

import org.junyinchen.collabtaskerbackend.models.Role;

public interface RoleService {
    Role getRole(String name);

    Role saveRole(Role role);
}
