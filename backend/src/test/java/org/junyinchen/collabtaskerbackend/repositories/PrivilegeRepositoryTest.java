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
    void savePrivilege() {
        Privilege newPrivilege = Privilege.builder().name("Test Role").build();
        Privilege insertedPrivilege = privilegeRepository.save(newPrivilege);
        assertEquals(
                testEntityManager.find(Privilege.class, insertedPrivilege.getId()), newPrivilege);
    }
}
