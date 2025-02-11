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
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
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

        customerStorage.addCustomer(Customer.builder().name("Ivan Petrov").legPower(15).handPower(1).iq(100).build());
        customerStorage.addCustomer(Customer.builder().name("Julia").legPower(7).handPower(10).iq(101).build());
        customerStorage.addCustomer(Customer.builder().name("AI").legPower(42).handPower(42).iq(333).build());

        hseCarService.sellCars();
        customerStorage.getCustomers().forEach(customer -> Assertions.assertNotNull(customer.getCar()));
    }

    @Test
    @DisplayName("Продажа только части машин пользователям")
    void partialSellTest() {
        carService.addCar(pedalCarFactory, new PedalEngineParams(1));
        carService.addCar(levitateCarFactory, EmptyEngineParams.DEFAULT);
        carService.addCar(handCarFactory, EmptyEngineParams.DEFAULT);


        customerStorage.addCustomer(Customer.builder().name("Victor").legPower(15).handPower(10).iq(84).build());
        customerStorage.addCustomer(Customer.builder().name("Arseniy").legPower(7).handPower(2).iq(82).build());
        customerStorage.addCustomer(Customer.builder().name("Gen").legPower(15).handPower(16).iq(300).build());

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
