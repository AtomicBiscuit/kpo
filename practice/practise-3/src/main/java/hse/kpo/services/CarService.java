package hse.kpo.services;

import hse.kpo.domains.Car;
import hse.kpo.domains.Customer;
import hse.kpo.interfaces.ICarFactory;
import hse.kpo.interfaces.ICarProvider;

import java.util.ArrayList;
import java.util.List;


/**
 Класс-хранилище автомобилей
 */
public class CarService implements ICarProvider {

    private final List<Car> cars = new ArrayList<>();

    private int carNumberCounter = 0;

    /**
     Извлекает автомобиль, который подойдёт покупателю, из хранилища

     @param customer покупатель
     @return подходящий автомобиль, либо null если такого не нашлось
     */
    @Override
    public Car takeCar(Customer customer) {
        var firstCar = cars.stream().filter(car -> car.isCompatible(customer)).findFirst();

        firstCar.ifPresent(cars::remove);

        return firstCar.orElse(null);
    }

    /**
     Создаёт и добавляет в хранилище автомобиль

     @param carFactory фабрика для порождения автомобилей
     @param carParams  параметры, для создания
     */
    public <TParams> void addCar(ICarFactory<TParams> carFactory, TParams carParams) {
        var car = carFactory.createCar(carParams, ++carNumberCounter);

        cars.add(car);
    }
}
