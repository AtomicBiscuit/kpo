package hse.kpo;

import hse.kpo.domains.Customer;
import hse.kpo.factories.cars.HandCarFactory;
import hse.kpo.factories.cars.PedalCarFactory;
import hse.kpo.observers.ReportSalesObserver;
import hse.kpo.params.EmptyEngineParams;
import hse.kpo.params.PedalEngineParams;
import hse.kpo.services.HseCarService;
import hse.kpo.storages.CarStorage;
import hse.kpo.storages.CustomerStorage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KpoApplicationTests {

    @Autowired
    private CarStorage carStorage;

    @Autowired
    private CustomerStorage customerStorage;

    @Autowired
    private HseCarService hseCarService;

    @Autowired
    private PedalCarFactory pedalCarFactory;

    @Autowired
    private HandCarFactory handCarFactory;

    @Autowired
    ReportSalesObserver observer;

    @Test
    @DisplayName("Тест загрузки контекста")
    void contextLoads() {
        Assertions.assertNotNull(carStorage);
        Assertions.assertNotNull(customerStorage);
        Assertions.assertNotNull(hseCarService);
    }

    @Test
    @DisplayName("Тест загрузки контекста")
    void hseCarServiceTest() {
        hseCarService.addObserver(observer);

        customerStorage.addCustomer(Customer.builder().name("Ivan1").legPower(6).handPower(4).build());
        customerStorage.addCustomer(Customer.builder().name("Maksim").legPower(4).handPower(6).build());
        customerStorage.addCustomer(Customer.builder().name("Petya").legPower(6).handPower(6).build());
        customerStorage.addCustomer(Customer.builder().name("Nikita").legPower(4).handPower(4).build());

        carStorage.addCar(pedalCarFactory, new PedalEngineParams(6));
        carStorage.addCar(pedalCarFactory, new PedalEngineParams(6));

        carStorage.addCar(handCarFactory, EmptyEngineParams.DEFAULT);
        carStorage.addCar(handCarFactory, EmptyEngineParams.DEFAULT);

        customerStorage.getCustomers().stream().map(Customer::toString).forEach(System.out::println);

        hseCarService.sellCars();

        customerStorage.getCustomers().stream().map(Customer::toString).forEach(System.out::println);

        System.out.println(observer.buildReport());
    }
}
