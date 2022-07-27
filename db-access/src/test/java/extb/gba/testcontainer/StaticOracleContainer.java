package extb.gba.testcontainer;

import org.springframework.util.unit.DataSize;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.OracleContainer;

public class StaticOracleContainer {
    public static OracleContainer getContainer() {
        return LazyOracleContainer.ORACLE_CONTAINER;
    }

    private static class LazyOracleContainer {
        private static final OracleContainer ORACLE_CONTAINER = makeContainer();

        private static OracleContainer makeContainer() {
            OracleContainer container = new OracleContainer()
                    // Username which testcontainers is going to use
                    // to find out if container is up and running
                    .withUsername("test")
                    // Password which testcontainers is going to use
                    // to find out if container is up and running
                    .withPassword("test")
                    // Tell testcontainers, that those ports should
                    // be mapped to external ports
                    .withExposedPorts(1521, 5500)
                    // Oracle database is not going to start if less
                    // than 1gb of shared memory is available, so this is necessary
                    .withSharedMemorySize(DataSize.ofGigabytes(2).toBytes());

            if (container.getDockerImageName().equals("gvenzl/oracle-xe:21.3.0")) {
                // This the same as giving the container
                // -v /path/to/init_db.sql:/opt/oracle/scripts/startup/init_db.sql
                // Oracle will execute init_db.sql, after container is started
                container = container.withClasspathResourceMapping("init_db.sql"
                        , "/opt/oracle/scripts/startup/init_db.sql"
                        , BindMode.READ_ONLY);
            }

            container.start();
            return container;
        }
    }
}
