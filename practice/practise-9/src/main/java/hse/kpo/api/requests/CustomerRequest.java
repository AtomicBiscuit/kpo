package hse.kpo.api.requests;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

public record CustomerRequest(
        @Schema(description = "Имя покупателя", example = "Vlad")
        @Pattern(regexp = "[a-zA-Z][a-zA-Z ]*")
        String name,

        @Schema(description = "Сила ног (1-15)", example = "6")
        @Min(value = 1, message = "Минимальная сила - 1")
        @Max(value = 15, message = "Максимальный сила - 15")
        Integer legPower,

        @Schema(description = "Сила рук (1-15)", example = "3")
        @Min(value = 1, message = "Минимальная сила - 1")
        @Max(value = 15, message = "Максимальный сила - 15")
        Integer handPower,

        @Schema(description = "Уровень iq (1-500)", example = "112")
        @Min(value = 1, message = "Минимальная уровень - 1")
        @Max(value = 500, message = "Максимальный уровень - 15")
        Integer iq
) {}