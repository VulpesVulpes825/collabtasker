package org.junyinchen.collabtaskerbackend.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junyinchen.collabtaskerbackend.DatabaseContainerInitializer;
import org.junyinchen.collabtaskerbackend.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RoleRepositoryTest implements DatabaseContainerInitializer {
    @Autowired RoleRepository roleRepository;

    @Autowired TestEntityManager testEntityManager;

    @Test
    void savePrivilege() {
        String name = "test";
        Role newRole = Role.builder().name(name).build();
        roleRepository.save(newRole);
        assertEquals(roleRepository.findByName(name), newRole);
    }
}
