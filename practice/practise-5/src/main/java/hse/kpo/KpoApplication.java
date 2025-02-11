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
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Класс платформы по учету автомобилей студентов.
 */
@SpringBootApplication
public class KpoApplication {
    /**
     * Эмулирует работу программы на тестовых данных и выводит результат в консоль.
     *
     * @param args аргументы командой строки
     */
    public static void main(String[] args) {
        var springApplicationContext = SpringApplication.run(KpoApplication.class, args);

        var customerStorage = springApplicationContext.getBean(CustomerStorage.class);
        customerStorage.addCustomer(Customer.builder().name("First").legPower(6).handPower(4).iq(108).build());
        customerStorage.addCustomer(Customer.builder().name("Second").legPower(4).handPower(6).iq(96).build());
        customerStorage.addCustomer(Customer.builder().name("Third").legPower(6).handPower(6).iq(301).build());
        customerStorage.addCustomer(Customer.builder().name("Fourth").legPower(4).handPower(4).iq(85).build());


        var carService = springApplicationContext.getBean(CarService.class);

        var levitateCarFactory = springApplicationContext.getBean(LevitateCarFactory.class);
        carService.addCar(levitateCarFactory, EmptyEngineParams.DEFAULT);

        var handCarFactory = springApplicationContext.getBean(HandCarFactory.class);
        carService.addCar(handCarFactory, EmptyEngineParams.DEFAULT);
        carService.addCar(handCarFactory, EmptyEngineParams.DEFAULT);

        var pedalCarFactory = springApplicationContext.getBean(PedalCarFactory.class);
        carService.addCar(pedalCarFactory, new PedalEngineParams(4));
        carService.addCar(pedalCarFactory, new PedalEngineParams(6));


        System.out.println("Before Selling: \n");

        customerStorage.getCustomers().stream().map(Customer::toString).forEach(System.out::println);

        var hseCarService = springApplicationContext.getBean(HseCarService.class);

        hseCarService.sellCars();

        System.out.println("\nAfter Selling: \n");

        customerStorage.getCustomers().stream().map(Customer::toString).forEach(System.out::println);
    }
}
