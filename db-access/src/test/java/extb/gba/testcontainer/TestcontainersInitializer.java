package extb.gba.testcontainer;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.OracleContainer;
import org.testcontainers.shaded.org.apache.commons.lang3.BooleanUtils;

public class TestcontainersInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private static final String TESTCONTAINER_ORACLE_OMIT = "testcontainer.oracle.omit";
    private static final Logger log = LoggerFactory.getLogger(TestcontainersInitializer.class);

    @Override
    public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {
        boolean enableOracleContainer = !BooleanUtils.toBoolean(System.getProperty(TESTCONTAINER_ORACLE_OMIT));
        log.info("enableOracleContainer: {}", enableOracleContainer);
        if (enableOracleContainer) {
            OracleContainer oracleContainer = StaticOracleContainer.getContainer();
            oracleContainer.followOutput(s -> log.debug(s.getUtf8String()));
            log.info("OracleContainer has started with jdbc: {}", oracleContainer.getJdbcUrl());

            prepareTestPropertyValues(oracleContainer).applyTo(
                    applicationContext.getEnvironment(), TestPropertyValues.Type.MAP, "testcontainers.oracle");
        }
    }

    protected TestPropertyValues prepareTestPropertyValues(JdbcDatabaseContainer<?> dbContainer) {
        final String jdbcUrl = dbContainer.getJdbcUrl();
        final String user = dbContainer.getUsername();
        final String password = dbContainer.getPassword();
        final String driverClass = dbContainer.getDriverClassName();
        return TestPropertyValues.of(
                "spring.datasource.driver-class-name=" + driverClass,
                "spring.datasource.username=" + user,
                "spring.datasource.password=" + password,
                "spring.datasource.url=" + jdbcUrl
        );
    }
}
