package hse.kpo;

import hse.kpo.domains.Customer;
import hse.kpo.factories.HandCarFactory;
import hse.kpo.factories.LevitateCarFactory;
import hse.kpo.factories.PedalCarFactory;
import hse.kpo.params.EmptyEngineParams;
import hse.kpo.params.PedalEngineParams;
import hse.kpo.services.CarService;
import hse.kpo.services.CustomerStorage;
import hse.kpo.services.HseCarService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KpoApplicationTests {

    @Autowired
    CarService carService;

    @Autowired
    CustomerStorage customerStorage;

    @Autowired
    HseCarService hseCarService;

    @Autowired
    PedalCarFactory pedalCarFactory;

    @Autowired
    HandCarFactory handCarFactory;

    @Autowired
    LevitateCarFactory levitateCarFactory;

    @Test
    @DisplayName("Тест загрузки контекста")
    void hseCarServiceTest() {
        customerStorage.addCustomer(new Customer("First", 6, 4, 108));
        customerStorage.addCustomer(new Customer("Second", 4, 6, 96));
        customerStorage.addCustomer(new Customer("Third", 6, 6, 301));
        customerStorage.addCustomer(new Customer("Fourth", 4, 4, 85));

        carService.addCar(levitateCarFactory, EmptyEngineParams.DEFAULT);

        carService.addCar(handCarFactory, EmptyEngineParams.DEFAULT);
        carService.addCar(handCarFactory, EmptyEngineParams.DEFAULT);

        carService.addCar(pedalCarFactory, new PedalEngineParams(4));
        carService.addCar(pedalCarFactory, new PedalEngineParams(6));

        System.out.println("Before Selling: \n");

        customerStorage.getCustomers().stream().map(Customer::toString).forEach(System.out::println);

        hseCarService.sellCars();

        System.out.println("\nAfter Selling: \n");

        customerStorage.getCustomers().stream().map(Customer::toString).forEach(System.out::println);
    }
}
