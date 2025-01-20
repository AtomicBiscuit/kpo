package studying;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        CarService cars = new CarService();
        CustomerStorage customers = new CustomerStorage();

        HseCarService hse = new HseCarService(cars, customers);

        PedalCarFactory pedalFactory = new PedalCarFactory();
        HandCarFactory handFactory = new HandCarFactory();

        customers.addCustomer(new Customer("First", 6, 4));
        customers.addCustomer(new Customer("Second", 4, 6));
        customers.addCustomer(new Customer("Third", 6, 6));
        customers.addCustomer(new Customer("Fourth", 4, 4));

        cars.addCar(handFactory, EmptyEngineParams.DEFAULT);
        cars.addCar(handFactory, EmptyEngineParams.DEFAULT);
        cars.addCar(pedalFactory, new PedalEngineParams(4));
        cars.addCar(pedalFactory, new PedalEngineParams(6));

        System.out.println("Before Selling: \n");

        for (final var customer : customers.getCustomers()) {
            System.out.println(customer.toString());
        }

        hse.sellCars();

        System.out.println("\nAfter Selling: \n");

        for (var customer : customers.getCustomers()) {
            System.out.println(customer.toString());
        }
    }
}
