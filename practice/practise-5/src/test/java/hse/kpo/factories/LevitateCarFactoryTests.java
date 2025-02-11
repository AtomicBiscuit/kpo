package hse.kpo.factories;

import static java.util.stream.IntStream.range;

import hse.kpo.params.EmptyEngineParams;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LevitateCarFactoryTests {
    @Autowired
    LevitateCarFactory levitateCarFactory;

    @Test
    @DisplayName("Проверка корректности создания фабрики авто")
    void dependencyInjectionTest() {
        Assertions.assertNotNull(levitateCarFactory);
    }

    @Test
    @DisplayName("Создание летающего автомобиля с номером 0")
    void createCarTest() {
        var car = levitateCarFactory.createCar(EmptyEngineParams.DEFAULT, 0);

        Assertions.assertEquals(0, car.getVin());
    }

    @Test
    @DisplayName("Создание 5 летающих автомобилей с разными номерами VIN")
    void createManyCarsTest() {
        range(0, 5).forEach(vin -> Assertions.assertEquals(
                vin, levitateCarFactory.createCar(EmptyEngineParams.DEFAULT, vin).getVin())
        );
    }
}
