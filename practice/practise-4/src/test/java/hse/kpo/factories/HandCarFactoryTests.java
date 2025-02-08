package hse.kpo.factories;

import static java.util.stream.IntStream.range;

import hse.kpo.params.EmptyEngineParams;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HandCarFactoryTests {
    @Autowired
    HandCarFactory handCarFactory;

    @Test
    @DisplayName("Проверка корректности создания фабрики авто")
    void dependencyInjectionTest() {
        Assertions.assertNotNull(handCarFactory);
    }

    @Test
    @DisplayName("Создание автомобиля с ручным двигателем c номером 0")
    void createCarTest() {
        var car = handCarFactory.createCar(EmptyEngineParams.DEFAULT, 0);

        Assertions.assertEquals(0, car.getVin());
    }

    @Test
    @DisplayName("Создание 5 автомобилей с ручными двигателями с разными номерами VIN")
    void createManyCarsTest() {
        range(0, 5).forEach(vin -> Assertions.assertEquals(
                vin, handCarFactory.createCar(EmptyEngineParams.DEFAULT, vin).getVin())
        );
    }
}
