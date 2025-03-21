package hse.kpo.export.transport.impls;

import hse.kpo.export.transport.TransportExporter;
import hse.kpo.interfaces.Transport;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class CSVTransportExporter implements TransportExporter {
    String getFormated(Transport transport) {
        return String.format("%d,%s,%s%n", transport.getVin(), transport.getTransportType(), transport.getEngineType());
    }

    @Override
    public void export(List<Transport> transports, Writer writer) throws IOException {
        transports.forEach(transport -> {
            try {
                writer.write(getFormated(transport));
                writer.flush();
            } catch (IOException ignored) {
            }
        });
    }
}