package hse.kpo.importer.transport.impls;

import hse.kpo.enums.ProductionTypes;
import hse.kpo.facade.Hse;
import hse.kpo.importer.transport.TransportImporter;
import hse.kpo.interfaces.Transport;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class CSVTransportImporter implements TransportImporter {
    private Transport mapper(String[] obj) {
        if (obj.length != 3) {
            return null;
        }
        var id = Integer.parseInt(obj[0]);
        var transportType = ProductionTypes.valueOf(obj[1]);
        var engineType = obj[2];
        return null;
    }

    @Override
    public List<Transport> importTransport(InputStream reader) throws IOException {
        var data = Arrays.stream(Arrays.toString(reader.readAllBytes()).split("\n")).toList();
        data.removeFirst();
        data.stream().map(string -> string.split(",")).map(this::mapper);
        return List.of();
    }
}
