package zoo.web.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

/**
 * DTO для перемещения животных между вольерами.
 */
@Builder
public record AnimalMoveRequest(
        @Schema(description = "Идентификатор животн", example = "42")
        @Min(value = 0, message = "Минимальное значение 0")
        @NotNull
        Integer animalId,

        @Schema(description = "Идентификатор вольера", example = "100")
        Integer enclosureId
) {}
