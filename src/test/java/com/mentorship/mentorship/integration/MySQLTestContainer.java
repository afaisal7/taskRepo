package com.mentorship.mentorship.integration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.TestConfiguration;
import org.testcontainers.containers.MySQLContainer;

@TestConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MySQLTestContainer {

    private static final String IMAGE_VERSION = "mysql:8.0.26";
    private static MySQLContainer<?> mySQLContainer;

    @BeforeAll
    public static void setUp() {
        mySQLContainer = new MySQLContainer<>(IMAGE_VERSION)
                .withDatabaseName("task_db")
                .withUsername("root")
                .withPassword("root");
        mySQLContainer.start();
    }

    public static String getJdbcUrl() {
        return mySQLContainer.getJdbcUrl();
    }

    public static String getUsername() {
        return mySQLContainer.getUsername();
    }

    public static String getPassword() {
        return mySQLContainer.getPassword();
    }
}
