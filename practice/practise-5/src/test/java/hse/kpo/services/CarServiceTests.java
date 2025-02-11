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
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
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
        var customer = Customer.builder().name("Strong legs customer").legPower(6).handPower(3).iq(108).build();

        var car = carService.takeCar(customer);
        Assertions.assertEquals(1, car.getVin());
    }

    @Test
    @DisplayName("Добавления автомобиля с ручным двигателем в хранилище и присвоение его пользователю")
    void addHandCarTest() {
        carService.addCar(handCarFactory, EmptyEngineParams.DEFAULT);
        var customer = Customer.builder().name("Strong hands customer").legPower(1).handPower(10).iq(108).build();

        var car = carService.takeCar(customer);
        Assertions.assertEquals(1, car.getVin());
    }

    @Test
    @DisplayName("Провал поиска авто с педальным двигателем, так как для него нужен параметр legPower >= 4")
    void takePedalCarFailedTest() {
        carService.addCar(pedalCarFactory, new PedalEngineParams(11));
        var customer = Customer.builder().name("Weak customer").legPower(1).handPower(1).iq(80).build();

        Assertions.assertNull(carService.takeCar(customer));
    }

    @Test
    @DisplayName("Провал поиска авто с ручным двигателем, так как для него нужен параметр handPower >= 4")
    void takeHandCarFailedTest() {
        carService.addCar(handCarFactory, EmptyEngineParams.DEFAULT);
        var customer = Customer.builder().name("Weak customer").legPower(2).handPower(2).iq(135).build();

        Assertions.assertNull(carService.takeCar(customer));
    }

    @Test
    @DisplayName("Провал поиска летающего авто, так как для них нужен параметр iq >= 300")
    void takeLevitateCarFailedTest() {
        carService.addCar(levitateCarFactory, EmptyEngineParams.DEFAULT);
        var customer = Customer.builder().name("Average customer").legPower(3).handPower(3).iq(85).build();

        Assertions.assertNull(carService.takeCar(customer));
    }
}
