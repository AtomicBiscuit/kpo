package zoo.web.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
import lombok.Builder;

/**
 * DTO для перепланирования кормления.
 */
@Builder
public record ScheduleUpdateRequest(
        @Schema(description = "ID расписания", example = "101")
        @NotNull
        Integer scheduleId,

        @Schema(description = "Время", example = "13:30:00")
        @NotNull
        LocalTime dailyTime,

        @Schema(description = "Вид еды", example = "milk")
        @NotBlank
        String foodName
) {}
