package com.evilcorp.demo;

import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.OracleContainer;
import org.testcontainers.shaded.org.apache.commons.lang3.BooleanUtils;

public class TestcontainersInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    static Logger log = LoggerFactory.getLogger(TestcontainersInitializer.class);

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        final String testcontainersEnabled = applicationContext.getEnvironment().getProperty("testcontainers.enabled");
        if (!BooleanUtils.toBoolean(testcontainersEnabled)) {
            return;
        }
        OracleContainer oracleContainer = StaticOracleContainer.getContainer();
        oracleContainer.followOutput(s -> log.debug(() -> s.getUtf8String()));

        final String jdbcUrl = oracleContainer.getJdbcUrl(); // jdbc:oracle:thin:@localhost:60146/xepdb1
        // Username and password which are defined in init_db.sql
        // Those should be used by application
        final String user = oracleContainer.getUsername();
        final String password = oracleContainer.getPassword();
        final String driverClass = oracleContainer.getDriverClassName();
        TestPropertyValues.of(
                "spring.jpa.properties.hibernate.default_schema=" + user,
                "spring.datasource.driver-class-name=" + driverClass,
                "spring.jpa.database-platform=org.hibernate.dialect.Oracle10gDialect",
                "spring.datasource.username=" + user,
                "spring.datasource.password=" + password,
                "spring.datasource.url=" + jdbcUrl,
                "spring.liquibase.url=" + jdbcUrl,
                "spring.liquibase.user=" + user,
                "spring.liquibase.password=" + password
        ).applyTo(applicationContext.getEnvironment(), TestPropertyValues.Type.MAP, "test");
    }
}
