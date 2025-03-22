package hse.kpo.export.transport.impls;

import hse.kpo.export.transport.TransportExporter;
import hse.kpo.interfaces.Transport;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class XMLTransportExporter implements TransportExporter {
    String getFormated(Transport transport) {
        return String.format(
                """
                        <Vehicle>
                            <VIN>%d</VIN>
                            <Type>%s</Type>
                            <Engine>
                                <Type>%s</Type>
                            </Engine>
                        </Vehicle>
                        """, transport.getVin(), transport.getTransportType(), transport.getEngineType()
        );
    }

    @Override
    public void export(List<Transport> transports, Writer writer) throws IOException {
        transports.forEach(transport -> {
            try {
                writer.write(getFormated(transport));
            } catch (IOException ignored) {
            }
        });
        writer.flush();
    }
}