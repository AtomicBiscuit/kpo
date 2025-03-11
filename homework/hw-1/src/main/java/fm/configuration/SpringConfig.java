package fm.configuration;

import fm.helpers.ConsoleHelper;
import java.text.SimpleDateFormat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    @Bean
    public ConsoleHelper consoleHelper() {
        return new ConsoleHelper(System.in, System.out, simpleDateFormat());
    }

    @Bean
    public SimpleDateFormat simpleDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd");
    }
}
