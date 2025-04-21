package zoo.web.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

/**
 * DTO для кормления животных.
 */
@Builder
public record AnimalFeedRequest(
        @Schema(description = "Идентификатор", example = "42")
        @NotNull
        @Min(value = 0, message = "Минимальное значение 0")
        Integer id,

        @Schema(description = "Наименование еды", example = "apple")
        @NotBlank
        String foodName
) {}
