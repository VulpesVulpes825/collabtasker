package org.junyinchen.collabtaskerbackend.repositories;

import org.junyinchen.collabtaskerbackend.models.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    Privilege findByName(String name);
}
