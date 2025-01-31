package hse.kpo.services;

import hse.kpo.domains.Car;
import hse.kpo.domains.Customer;
import hse.kpo.interfaces.ICarFactory;
import hse.kpo.interfaces.ICarProvider;

import java.util.ArrayList;
import java.util.List;

public class CarService implements ICarProvider {

    private final List<Car> cars = new ArrayList<>();

    private int carNumberCounter = 0;

    @Override
    public Car takeCar(Customer customer) {
        var firstCar = cars.stream().filter(car -> car.isCompatible(customer)).findFirst();

        firstCar.ifPresent(cars::remove);

        return firstCar.orElse(null);
    }

    public <TParams> void addCar(ICarFactory<TParams> carFactory, TParams carParams) {
        // создаем автомобиль из переданной фабрики
        var car = carFactory.createCar(carParams, ++carNumberCounter);

        cars.add(car); // добавляем автомобиль
    }
}
