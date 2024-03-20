package org.junyinchen.collabtaskerbackend.repositories;

import org.junyinchen.collabtaskerbackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
