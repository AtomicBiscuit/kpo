package zoo.web.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
import lombok.Builder;

/**
 * DTO для планирования кормления.
 */
@Builder
public record ScheduleCreateRequest(
        @Schema(description = "ID животного", example = "42")
        @NotNull
        Integer animalId,

        @Schema(description = "Время", example = "13:30:00")
        @NotNull
        LocalTime dailyTime,

        @Schema(description = "Вид еды", example = "milk")
        @NotBlank
        String foodName
) {}
