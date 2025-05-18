package zoo.web.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
public record EnclosureCreateRequest(
        @Schema(description = "Тип вольера", example = "aquarium")
        @NotBlank
        String type,

        @Schema(description = "Геометрический размер", example = "20000")
        @Min(value = 0)
        @NotNull
        Integer size,

        @Schema(description = "Вместимость", example = "150")
        @Min(value = 0)
        @NotNull
        Integer capacity
) {}
