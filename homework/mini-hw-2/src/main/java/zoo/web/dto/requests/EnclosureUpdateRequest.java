package zoo.web.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

/**
 * DTO для добавления вольеров.
 *
 * @param type     Тип вольера
 * @param size     Размеры вольера
 * @param capacity Вместимость вольера
 */
@Builder
public record EnclosureUpdateRequest(
        @Schema(description = "Идентификатор", example = "42")
        @NotNull
        @Min(value = 0, message = "Минимальное значение 0")
        Integer id,

        @Schema(description = "Тип вольера", example = "aquarium")
        String type,

        @Schema(description = "Геометрический размер", example = "20000")
        @Min(value = 0)
        Integer size,

        @Schema(description = "Вместимость", example = "150")
        @Min(value = 0)
        Integer capacity
) {}
