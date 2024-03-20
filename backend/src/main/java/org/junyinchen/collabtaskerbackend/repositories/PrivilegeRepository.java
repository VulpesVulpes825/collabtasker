package org.junyinchen.collabtaskerbackend.repositories;

import org.junyinchen.collabtaskerbackend.models.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    @Query("SELECT u FROM Privilege u WHERE u.name = :name")
    Privilege findByName(@Param("name") String name);
}
