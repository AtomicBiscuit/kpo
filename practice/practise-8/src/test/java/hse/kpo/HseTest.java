package hse.kpo;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

import hse.kpo.domains.Customer;
import hse.kpo.domains.cars.Car;
import hse.kpo.enums.ProductionTypes;
import hse.kpo.enums.ReportFormat;
import hse.kpo.enums.TransportFormat;
import hse.kpo.facade.Hse;
import hse.kpo.factories.cars.HandCarFactory;
import hse.kpo.factories.cars.PedalCarFactory;
import hse.kpo.interfaces.SalesObserver;
import hse.kpo.storages.CustomerStorage;
import hse.kpo.storages.cars.CarStorage;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
class HseTest {

    @Autowired
    private Hse hse;

    @Autowired
    private CustomerStorage customerStorage;

    @Autowired
    private CarStorage carStorage;

    @Autowired
    private PedalCarFactory pedalCarFactory;

    @Autowired
    private HandCarFactory handCarFactory;

    @Mock
    private SalesObserver salesObserver;

    @Test
    @DisplayName("Клиент с handPower=6 должен получить автомобиль с ручным приводом")
    void customerWithEnoughHandPower_ShouldGetHandCar() {
        // Arrange
        hse.addCustomer("Test", 3, 6, 100);
        hse.addHandCar();

        // Act
        hse.sell();

        // Assert
        Customer customer = customerStorage.getCustomers().get(0);
        Car receivedCar = customer.getCar();

        assertAll(() -> assertNotNull(receivedCar, "Автомобиль не был назначен"));
    }

    @Test
    @DisplayName("Клиент с handPower=4 не должен получить автомобиль с ручным приводом")
    void customerWithLowHandPower_ShouldNotGetHandCar() {
        // Arrange
        hse.addCustomer("Test", 3, 4, 100);
        hse.addHandCar();

        // Act
        hse.sell();

        // Assert
        Customer customer = customerStorage.getCustomers().get(0);

        assertAll(() -> assertNull(customer.getCar(), "Клиент не должен был получить автомобиль. Проверьте " +
                                                              "совместимость двигателя"));
    }

    @Test
    @DisplayName("При продаже двух автомобилей разным клиентам оба должны получить машины")
    void multipleCustomers_ShouldGetDifferentCars() {
        // Arrange
        hse.addCustomer("First", 3, 6, 100);
        hse.addCustomer("Second", 5, 7, 110);
        hse.addHandCar();
        hse.addHandCar();

        // Act
        hse.sell();

        // Assert
        List<Customer> customers = customerStorage.getCustomers();
        assertAll(() -> assertNotNull(customers.get(0)
                                               .getCar(), "Первый клиент должен получить автомобиль"),
                  () -> assertNotNull(customers.get(1)
                                                                                                                                    .getCar(), "Второй клиент должен получить автомобиль"), () -> assertNotEquals(customers.get(0)
                                                                                                                                                                                                                           .getCar()
                                                                                                                                                                                                                           .getVin(), customers.get(1)
                                                                                                                                                                                                                                               .getCar()
                                                                                                                                                                                                                                               .getVin(), "VIN автомобилей должны отличаться"));
    }

    @Test
    @DisplayName("Отчет должен содержать информацию о продажах")
    void report_ShouldContainSalesInformation() {
        // Arrange
        // Добавляем клиента с параметрами, подходящими для педального автомобиля
        hse.addCustomer("TestClient", 7, 5, 100); // legPower=7 > 5 (требование PedalEngine)

        // Добавляем автомобиль с педальным двигателем (размер педалей 6)
        hse.addPedalCar(6);

        // Act
        hse.sell(); // Выполняем продажу
        String report = hse.generateReport(); // Генерируем отчет

        // Assert
        assertAll(() -> assertTrue(report.contains("TestClient"), "В отчете должно быть имя клиента"),
                  () -> assertTrue(report.contains(ProductionTypes.CAR.toString()), "В отчете должен быть указан тип "
                                                                                            + "продукции 'CAR'"),
                  () -> assertTrue(report.matches("(?s).*VIN-\\d+.*"), "Отчет должен " + "содержать VIN автомобиля в "
                                                                               + "формате 'VIN-число'"));
    }

    @Test
    @DisplayName("")
    void reportExporterMarkdown_ShouldContainSalesInformation() {
        var correctReport = List.of("Покупатели: - Customer(name=TestClient, legPower=7, handPower=5, iq=100, " +
                                            "car=null, catamaran=null)", "Операция: Продажа: CAR VIN-1 клиенту " + "TestClient (Сила рук: 5, Сила ног: 7, IQ: 100)", "Покупатели: - " + "Customer" + "(name" + "=TestClient, " + "legPower=7, handPower=5, iq=100, car=Car" + "(engine" + "=PedalEngine" + "(size=1)" + ", " + "vin=1), " + "catamaran=null)");

        hse.addCustomer("TestClient", 7, 5, 100);
        hse.addPedalCar(1);
        hse.sell();

        var buf = new ByteArrayOutputStream();
        Assertions.assertDoesNotThrow(() -> hse.exportReport(ReportFormat.MARKDOWN, new PrintWriter(buf)));

        var report = buf.toString();
        correctReport.forEach(correct -> assertTrue(report.contains(correct)));
    }

    @Test
    @DisplayName("")
    void reportExporterJSONShouldContainSalesInformation() {
        var correctReport = "{\"title\":\"Отчет за 2025-03-01\",\"content\":\"Покупатели: - Customer(name=TestClient,"
                                    + " legPower=7, handPower=5, iq=100, car=null, catamaran=null)\\nОперация: " +
                                    "Продажа: CAR VIN-1 " + "клиенту" + " " + "TestClient (Сила рук: 5, Сила ног: 7, "
                                    + "IQ: 100)" + "\\r\\nПокупатели: - Customer" + "(name=TestClient, " + "legPower" +
                                    "=7," + " handPower=5, iq=100, car=Car" + "(engine=PedalEngine(size=1), vin=1), " + "catamaran=null)\\n\"}";

        hse.addCustomer("TestClient", 7, 5, 100);
        hse.addPedalCar(1);
        hse.sell();

        var buf = new ByteArrayOutputStream();
        Assertions.assertDoesNotThrow(() -> hse.exportReport(ReportFormat.JSON, new PrintWriter(buf)));

        var report = buf.toString();
        assertEquals(correctReport, report);
    }

    @Test
    @DisplayName("")
    void transportExporterXML() {
        hse.addPedalCar(1);
        hse.addPedalCatamaran(51);

        var buf = new ByteArrayOutputStream();
        Assertions.assertDoesNotThrow(() -> hse.exportTransport(TransportFormat.XML, new PrintWriter(buf)));

        var report = buf.toString();
        System.out.println(report);
    }
}