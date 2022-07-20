# oracle-testcontainer-example

This is a demo project to show the easiest way to
use testcontainers library with Spring Boot.

Oracle container is started by singleton class com.evilcorp.demo.StaticOracleContainer.
It allows to use single oracle instance for all tests.

Tests connects to Oracle container by `@ContextConfiguration(initializers = TestcontainersInitializer.class)`

An approach to prepare and use Oracle container with precooked DB see in `dockerfiles/README.md`

