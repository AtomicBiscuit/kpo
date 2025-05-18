package zoo.web.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

/**
 * DTO для получения названия еды.
 */
@Builder
public record AnimalFoodRequest(
        @Schema(description = "Наименование еды", example = "apple")
        @NotBlank
        String foodName
) {}
