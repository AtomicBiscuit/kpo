package studying;

import studying.domains.Customer;
import studying.factories.HandCarFactory;
import studying.factories.LevitateCarFactory;
import studying.factories.PedalCarFactory;
import studying.params.EmptyEngineParams;
import studying.params.PedalEngineParams;
import studying.services.CarService;
import studying.services.CustomerStorage;
import studying.services.HseCarService;

public class Main {
    public static void main(String[] args) {
        var carService = new CarService();

        var customerStorage = new CustomerStorage();

        var hseCarService = new HseCarService(carService, customerStorage);

        var pedalCarFactory = new PedalCarFactory();
        var handCarFactory = new HandCarFactory();
        var levitateCarFactory = new LevitateCarFactory();


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
