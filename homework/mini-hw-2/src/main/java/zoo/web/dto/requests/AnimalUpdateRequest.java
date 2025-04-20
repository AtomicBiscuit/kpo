package zoo.web.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.Date;
import lombok.Builder;

/**
 * DTO для обновления информации о животных.
 *
 * @param type         Вид животного
 * @param name         Кличка животного
 * @param birthday     День рождения животного
 * @param sex          Пол животного
 * @param favoriteFood Любимая еда животного
 * @param isHealthy    Статус здоровья животного
 */
@Builder
public record AnimalUpdateRequest(
        @Schema(description = "Идентификатор", example = "42")
        @NotNull
        @Min(value = 0, message = "Минимальное значение 0")
        Integer id,

        @Schema(description = "Вид", example = "Tiger")
        String type,

        @Schema(description = "Кличка", example = "Jojo")
        String name,

        @Schema(description = "Дата рождения", example = "2000-05-06")
        Date birthday,

        @Schema(description = "Пол", example = "male")
        @Pattern(regexp = "male|female|hermaphrodite")
        String sex,

        @Schema(description = "Любимая еда", example = "carrot")
        String favoriteFood,

        @Schema(description = "Статус здоровья", example = "true")
        Boolean isHealthy
) {}
