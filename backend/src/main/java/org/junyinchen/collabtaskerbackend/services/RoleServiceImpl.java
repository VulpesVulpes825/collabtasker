package org.junyinchen.collabtaskerbackend.services;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

import org.junyinchen.collabtaskerbackend.models.Role;
import org.junyinchen.collabtaskerbackend.repositories.RoleRepository;
import org.springframework.stereotype.Service;

@Transactional
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role getRole(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }
}
