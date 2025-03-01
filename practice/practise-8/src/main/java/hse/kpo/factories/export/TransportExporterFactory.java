package hse.kpo.factories.export;

import hse.kpo.enums.TransportFormat;
import hse.kpo.export.transport.TransportExporter;
import hse.kpo.export.transport.impls.CSVTransportExporter;
import hse.kpo.export.transport.impls.XMLTransportExporter;
import org.springframework.stereotype.Component;

@Component
public class TransportExporterFactory {
    public TransportExporter create(TransportFormat format) {
        return switch (format) {
            case XML -> new XMLTransportExporter();
            case CSV -> new CSVTransportExporter();
            default -> throw new IllegalArgumentException("Unsupported format: " + format);
        };
    }
}
