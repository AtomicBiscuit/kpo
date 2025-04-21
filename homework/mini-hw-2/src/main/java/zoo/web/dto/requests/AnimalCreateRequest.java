package zoo.web.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import lombok.Builder;

/**
 * DTO для добавления животных.
 *
 * @param type         Вид животного
 * @param name         Кличка животного
 * @param birthday     День рождения животного
 * @param sex          Пол животного
 * @param favoriteFood Любимая еда животного
 * @param isHealthy    Статус здоровья животного
 */
@Builder
public record AnimalCreateRequest(
        @Schema(description = "Вид", example = "Tiger")
        @NotBlank
        String type,

        @Schema(description = "Кличка", example = "Jojo")
        @NotBlank
        String name,

        @Schema(description = "Дата рождения", example = "2000-01-01")
        @NotNull
        LocalDate birthday,

        @Schema(description = "Пол", example = "male")
        @Pattern(regexp = "male|female|hermaphrodite")
        @NotBlank
        String sex,

        @Schema(description = "Любимая еда", example = "carrot")
        @NotBlank
        String favoriteFood,

        @Schema(description = "Статус здоровья", example = "true")
        @NotNull
        Boolean isHealthy
) {}
