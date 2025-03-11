package fm.configuration;

import fm.helpers.ConsoleReader;
import java.text.SimpleDateFormat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    @Bean
    public ConsoleReader consoleReader() {
        return new ConsoleReader(System.in, System.out);
    }

    @Bean
    public SimpleDateFormat simpleDateFormat() {
        return new SimpleDateFormat("yy-MM-dd");
    }
}
