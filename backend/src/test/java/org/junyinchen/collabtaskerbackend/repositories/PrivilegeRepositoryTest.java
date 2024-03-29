package org.junyinchen.collabtaskerbackend.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junyinchen.collabtaskerbackend.DatabaseContainerInitializer;
import org.junyinchen.collabtaskerbackend.models.Privilege;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PrivilegeRepositoryTest implements DatabaseContainerInitializer {
    @Autowired PrivilegeRepository privilegeRepository;

    @Autowired TestEntityManager testEntityManager;

    @Test
    void testFindByName() {
        String name = "Test";
        Privilege newPrivilege = Privilege.builder().name(name).build();
        privilegeRepository.save(newPrivilege);
        assertEquals(privilegeRepository.findByName(name), newPrivilege);
    }
}
