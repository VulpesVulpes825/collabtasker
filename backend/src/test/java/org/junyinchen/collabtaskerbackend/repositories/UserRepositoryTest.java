package org.junyinchen.collabtaskerbackend.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junyinchen.collabtaskerbackend.DatabaseContainerInitializer;
import org.junyinchen.collabtaskerbackend.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest implements DatabaseContainerInitializer {
    @Autowired UserRepository userRepository;

    @Autowired TestEntityManager testEntityManager;

    @Test
    void testFindByUsernameQuery() {
        String username = "username";
        User newUser = User.builder().username(username).build();
        userRepository.save(newUser);
        assertEquals(userRepository.findByUsernameQuery(username), newUser);
    }
}
