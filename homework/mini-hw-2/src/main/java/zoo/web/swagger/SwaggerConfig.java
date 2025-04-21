package zoo.web.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Класс для конфигурации Swagger.
 */
@Configuration
public class SwaggerConfig {
    /**
     * Bean для OpenAPI.
     */
    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI().info(new Info().title("Zoo API")
                                            .version("1.0")
                                            .description("API для управления зоопарком"));
    }
}