package hse.kpo.factories;

import static java.util.stream.IntStream.range;

import hse.kpo.params.PedalEngineParams;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PedalCarFactoryTests {
    @Autowired
    PedalCarFactory pedalCarFactory;

    @Test
    @DisplayName("Проверка корректности создания фабрики авто")
    void dependencyInjectionTest() {
        Assertions.assertNotNull(pedalCarFactory);
    }

    @Test
    @DisplayName("Создание автомобиля с педальным двигателем c параметром pedalSize 4 и номером 0")
    void createCarTest() {
        var car = pedalCarFactory.createCar(new PedalEngineParams(4), 0);

        Assertions.assertEquals(0, car.getVin());
    }

    @Test
    @DisplayName("Создание 5 автомобилей с педальными двигателями с разными номерами VIN")
    void createManyCarsTest() {
        range(0, 5).forEach(vin -> Assertions.assertEquals(
                vin, pedalCarFactory.createCar(new PedalEngineParams(132), vin).getVin())
        );
    }
}
