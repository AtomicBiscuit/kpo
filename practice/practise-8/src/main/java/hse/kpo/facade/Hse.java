package hse.kpo.facade;

import hse.kpo.domains.Customer;
import hse.kpo.domains.cars.CatamaranWithWheels;
import hse.kpo.domains.catamarans.Catamaran;
import hse.kpo.enums.ReportFormat;
import hse.kpo.enums.TransportFormat;
import hse.kpo.factories.cars.HandCarFactory;
import hse.kpo.factories.cars.LevitationCarFactory;
import hse.kpo.factories.cars.PedalCarFactory;
import hse.kpo.factories.catamarans.HandCatamaranFactory;
import hse.kpo.factories.catamarans.LevitationCatamaranFactory;
import hse.kpo.factories.catamarans.PedalCatamaranFactory;
import hse.kpo.factories.export.ReportExporterFactory;
import hse.kpo.factories.export.TransportExporterFactory;
import hse.kpo.interfaces.SalesObserver;
import hse.kpo.params.EmptyEngineParams;
import hse.kpo.params.PedalEngineParams;
import hse.kpo.services.cars.HseCarService;
import hse.kpo.services.catamarans.HseCatamaranService;
import hse.kpo.storages.CustomerStorage;
import hse.kpo.storages.cars.CarStorage;
import hse.kpo.storages.catamarans.CatamaranStorage;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.Writer;
import java.util.Random;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Фасад для работы с системой продажи транспортных средств.
 * Предоставляет упрощенный интерфейс для управления клиентами,
 * транспортом и процессами продаж.
 */
@Component
@RequiredArgsConstructor
public class Hse {
    private final CustomerStorage customerStorage;

    private final CarStorage carStorage;

    private final CatamaranStorage catamaranStorage;

    private final HseCarService carService;

    private final HseCatamaranService catamaranService;

    private final SalesObserver salesObserver;

    private final PedalCarFactory pedalCarFactory;

    private final HandCarFactory handCarFactory;

    private final LevitationCarFactory levitationCarFactory;

    private final PedalCatamaranFactory pedalCatamaranFactory;

    private final HandCatamaranFactory handCatamaranFactory;

    private final LevitationCatamaranFactory levitationCatamaranFactory;

    private final ReportExporterFactory reportExporterFactory;

    private final TransportExporterFactory transportExporterFactory;

    @PostConstruct
    private void init() {
        carService.addObserver(salesObserver);
    }

    /**
     * Добавляет нового клиента в систему.
     *
     * @param name      имя клиента
     * @param legPower  сила ног (1-10)
     * @param handPower сила рук (1-10)
     * @param iq        уровень интеллекта (1-200)
     * @example hse.addCustomer(" Анна ", 7, 5, 120);
     */
    public void addCustomer(String name, int legPower, int handPower, int iq) {
        Customer customer = Customer.builder().name(name).legPower(legPower).handPower(handPower).iq(iq).build();
        customerStorage.addCustomer(customer);
    }

    /**
     * Добавляет педальный автомобиль в систему.
     *
     * @param pedalSize размер педалей (1-15)
     */
    public void addPedalCar(int pedalSize) {
        carStorage.addCar(pedalCarFactory, new PedalEngineParams(pedalSize));
    }

    /**
     * Добавляет автомобиль с ручным приводом.
     */
    public void addHandCar() {
        carStorage.addCar(handCarFactory, EmptyEngineParams.DEFAULT);
    }

    /**
     * Добавляет левитирующий автомобиль.
     */
    public void addLevitationCar() {
        carStorage.addCar(levitationCarFactory, EmptyEngineParams.DEFAULT);
    }

    public void addWheelCatamaran() {
        carStorage.addExistingCar(new CatamaranWithWheels(createCatamaran()));
    }

    private Catamaran createCatamaran() {
        var engineCount = new Random().nextInt(3);

        return switch (engineCount) {
            case 0 -> catamaranStorage.addCatamaran(handCatamaranFactory, EmptyEngineParams.DEFAULT);
            case 1 -> catamaranStorage.addCatamaran(pedalCatamaranFactory, new PedalEngineParams(6));
            case 2 -> catamaranStorage.addCatamaran(levitationCatamaranFactory, EmptyEngineParams.DEFAULT);
            default -> throw new RuntimeException("nonono");
        };
    }

    /**
     * Добавляет педальный катамаран.
     *
     * @param pedalSize размер педалей (1-15)
     */
    public void addPedalCatamaran(int pedalSize) {
        catamaranStorage.addCatamaran(pedalCatamaranFactory, new PedalEngineParams(pedalSize));
    }

    /**
     * Добавляет катамаран с ручным приводом.
     */
    public void addHandCatamaran() {
        catamaranStorage.addCatamaran(handCatamaranFactory, EmptyEngineParams.DEFAULT);
    }

    /**
     * Добавляет левитирующий катамаран.
     */
    public void addLevitationCatamaran() {
        catamaranStorage.addCatamaran(levitationCatamaranFactory, EmptyEngineParams.DEFAULT);
    }

    /**
     * Запускает процесс продажи доступного транспорта.
     * Автомобили продаются перед катамаранами.
     */
    public void sell() {
        carService.sellCars();
        catamaranService.sellCatamarans();
    }

    /**
     * Генерирует отчет о продажах в заданном формате.
     *
     * @param format формат вывода отчёта
     * @param writer объект {@link Writer} для записи отчёта
     */
    public void exportReport(ReportFormat format, Writer writer) throws IOException {
        var exporter = reportExporterFactory.create(format);
        exporter.export(salesObserver.buildReport(), writer);
    }

    /**
     * Генерирует отчет о продажах.
     *
     * @return форматированная строка с отчетом
     * @example System.out.println(hse.generateReport ());
     */
    public String generateReport() {
        return salesObserver.buildReport().toString();
    }

    public void exportTransport(TransportFormat format, Writer writer) throws IOException {
        var exporter = transportExporterFactory.create(format);
        var transports = Stream.concat(carStorage.getCars().stream(), catamaranStorage.getCatamarans().stream())
                               .toList();
        exporter.export(transports, writer);
    }
}