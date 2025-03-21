package hse.kpo.api.requests;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;

public record SalesExportRequest(
        @Schema(description = "Формат данных (JSON, MARKDOWN)", example = "JSON")
        @Pattern(regexp = "JSON|MARKDOWN", message = "Допустимые значения: JSON, MARKDOWN")
        String reportType
) {}