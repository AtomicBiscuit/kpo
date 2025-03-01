package hse.kpo.factories.export;

import hse.kpo.enums.ReportFormat;
import hse.kpo.export.reports.ReportExporter;
import hse.kpo.export.reports.impls.JsonReportExporter;
import hse.kpo.export.reports.impls.MarkdownReportExporter;
import org.springframework.stereotype.Component;

@Component
public class ReportExporterFactory {
    public ReportExporter create(ReportFormat format) {
        return switch (format) {
            case JSON -> new JsonReportExporter();
            case MARKDOWN -> new MarkdownReportExporter();
            default -> throw new IllegalArgumentException("Unsupported format: " + format);
        };
    }
}
