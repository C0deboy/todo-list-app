package todolist.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("todolist")
@Import({ SpringMvcConfiguration.class,
    SpringDataConfiguration.class,
    SpringSecurityConfiguration.class })
public class SpringRootConfiguration {
}
