package extb.gba;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@TestConfiguration
@PropertySources({@PropertySource("classpath:/db-access.properties"), @PropertySource("classpath:/test.properties")})
@EnableAutoConfiguration
public class DBTestConfiguration {
}
