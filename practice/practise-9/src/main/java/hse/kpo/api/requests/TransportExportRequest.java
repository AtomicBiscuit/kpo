package hse.kpo.api.requests;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;

public record TransportExportRequest(
        @Schema(description = "Формат данных (CSV, XML)", example = "XML")
        @Pattern(regexp = "CSV|XML", message = "Допустимые значения: CSV, XML")
        String reportType
) {}