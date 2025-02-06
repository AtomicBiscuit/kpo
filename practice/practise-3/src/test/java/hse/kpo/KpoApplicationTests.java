package hse.kpo;

import hse.kpo.services.CarService;
import hse.kpo.services.CustomerStorage;
import hse.kpo.services.HseCarService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KpoApplicationTests {

    @Autowired
    private CarService carService;

    @Autowired
    private CustomerStorage customerStorage;

    @Autowired
    private HseCarService hseCarService;

    @Test
    @DisplayName("Тест загрузки контекста")
    void contextLoads() {
        Assertions.assertNotNull(carService);
        Assertions.assertNotNull(customerStorage);
        Assertions.assertNotNull(hseCarService);
    }
}
