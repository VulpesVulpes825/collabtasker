package org.junyinchen.collabtaskerbackend.repositories;

import org.junyinchen.collabtaskerbackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
