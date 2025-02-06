package hse.kpo.services;

import hse.kpo.domains.Customer;
import hse.kpo.factories.HandCarFactory;
import hse.kpo.factories.LevitateCarFactory;
import hse.kpo.factories.PedalCarFactory;
import hse.kpo.params.EmptyEngineParams;
import hse.kpo.params.PedalEngineParams;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class HseCarServiceTests {
    @Autowired
    CarService carService;

    @Autowired
    CustomerStorage customerStorage;

    @Autowired
    HseCarService hseCarService;

    @Autowired
    HandCarFactory handCarFactory;

    @Autowired
    LevitateCarFactory levitateCarFactory;

    @Autowired
    PedalCarFactory pedalCarFactory;

    @Test
    @DisplayName("Полное соответствие между машинами и пользователями")
    void completeSellTest() {
        carService.addCar(pedalCarFactory, new PedalEngineParams(1));
        carService.addCar(levitateCarFactory, EmptyEngineParams.DEFAULT);
        carService.addCar(handCarFactory, EmptyEngineParams.DEFAULT);

        customerStorage.addCustomer(new Customer("Ivan Petrov", 15, 1, 100));
        customerStorage.addCustomer(new Customer("Julia", 7, 10, 101));
        customerStorage.addCustomer(new Customer("AI", 42, 42, 333));

        hseCarService.sellCars();
        customerStorage.getCustomers().forEach(customer -> Assertions.assertNotNull(customer.getCar()));
    }

    @Test
    @DisplayName("Продажа только части машин пользователям")
    void partialSellTest() {
        carService.addCar(pedalCarFactory, new PedalEngineParams(1));
        carService.addCar(levitateCarFactory, EmptyEngineParams.DEFAULT);
        carService.addCar(handCarFactory, EmptyEngineParams.DEFAULT);

        customerStorage.addCustomer(new Customer("Victor", 15, 10, 84));
        customerStorage.addCustomer(new Customer("Arseniy", 7, 2, 82));
        customerStorage.addCustomer(new Customer("Gen", 15, 16, 300));

        hseCarService.sellCars();

        var result = new ArrayList<Integer>();
        customerStorage.getCustomers().forEach(customer -> {
            var car = customer.getCar();
            if (Objects.nonNull(car)) {
                result.add(car.getVin());
            } else {
                result.add(-1);
            }
        });
        Assertions.assertEquals(List.of(1, -1, 3), result);
    }
}
