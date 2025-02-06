package hse.kpo.services;

import hse.kpo.domains.Customer;
import hse.kpo.factories.HandCarFactory;
import hse.kpo.factories.LevitateCarFactory;
import hse.kpo.factories.PedalCarFactory;
import hse.kpo.params.EmptyEngineParams;
import hse.kpo.params.PedalEngineParams;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CarServiceTests {

    @Autowired
    CarService carService;

    @Autowired
    PedalCarFactory pedalCarFactory;

    @Autowired
    HandCarFactory handCarFactory;

    @Autowired
    LevitateCarFactory levitateCarFactory;

    @Test
    @DisplayName("Проверка корректности создания хранилища машин")
    void dependencyInjectionTest() {
        Assertions.assertNotNull(carService);
    }

    @Test
    @DisplayName("Добавления автомобиля с педальным двигателем в хранилище и присвоение его пользователю")
    void addPedalCarTest() {
        carService.addCar(pedalCarFactory, new PedalEngineParams(100));
        var customer = new Customer("Strong legs customer", 6, 3, 108);

        var car = carService.takeCar(customer);
        Assertions.assertEquals(1, car.getVin());
    }

    @Test
    @DisplayName("Добавления автомобиля с ручным двигателем в хранилище и присвоение его пользователю")
    void addHandCarTest() {
        carService.addCar(handCarFactory, EmptyEngineParams.DEFAULT);
        var customer = new Customer("Strong hands customer", 1, 10, 108);

        var car = carService.takeCar(customer);
        Assertions.assertEquals(1, car.getVin());
    }

    @Test
    @DisplayName("Провал поиска авто с педальным двигателем, так как для него нужен параметр legPower >= 4")
    void takePedalCarFailedTest() {
        carService.addCar(pedalCarFactory, new PedalEngineParams(11));
        var customer = new Customer("Weak customer", 1, 1, 80);

        Assertions.assertNull(carService.takeCar(customer));
    }

    @Test
    @DisplayName("Провал поиска авто с ручным двигателем, так как для него нужен параметр handPower >= 4")
    void takeHandCarFailedTest() {
        carService.addCar(handCarFactory, EmptyEngineParams.DEFAULT);
        var customer = new Customer("Weak customer", 2, 2, 135);

        Assertions.assertNull(carService.takeCar(customer));
    }

    @Test
    @DisplayName("Провал поиска летающего авто, так как для них нужен параметр iq >= 300")
    void takeLevitateCarFailedTest() {
        carService.addCar(levitateCarFactory, EmptyEngineParams.DEFAULT);
        var customer = new Customer("Average customer", 3, 3, 85);

        Assertions.assertNull(carService.takeCar(customer));
    }
}
