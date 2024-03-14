package org.junyinchen.collabtaskerbackend.security;

import org.junyinchen.collabtaskerbackend.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class ApplicationConfig {
    private final UserRepo repo;

    @Autowired
    public ApplicationConfig(UserRepo repo) {
        this.repo = repo;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username ->
                (UserDetails)
                        repo.findByUsername(username)
                                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
