package com.goorm.BITA.config;

import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
public abstract class MySQLContainerTest {

    private static final String MYSQL_VERSION = "mysql:8";

    @Container
    public static final MySQLContainer mysql = new MySQLContainer(MYSQL_VERSION)
        .withUsername("bita")
        .withDatabaseName("bita")
        .withPassword("bita1234");
}