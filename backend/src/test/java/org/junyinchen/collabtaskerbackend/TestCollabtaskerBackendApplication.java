package org.junyinchen.collabtaskerbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class TestCollabtaskerBackendApplication {
    public static void main(String[] args) {
        SpringApplication.from(CollabtaskerBackendApplication::main)
                .with(TestCollabtaskerBackendApplication.class)
                .run(args);
    }

    @Bean
    @ServiceConnection
    MySQLContainer<?> mysqlContainer() {
        return new MySQLContainer<>(DockerImageName.parse("mysql:latest"));
    }
}
