package hse.kpo.importer.transport;

import hse.kpo.interfaces.Transport;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface TransportImporter {
    List<Transport> importTransport(InputStream reader) throws IOException;
}