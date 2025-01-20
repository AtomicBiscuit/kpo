package studying;

import java.util.*;

public class CarService implements ICarProvider {

    private final List<Car> cars = new ArrayList<>();

    private int carNumberCounter = 0;

    @Override
    public Car takeCar(Customer customer) {
        var firstCar = cars.stream().filter(car -> car.isCompatible(customer)).findFirst();

        if (firstCar.isPresent()) {
            cars.remove(firstCar.get());
            return firstCar.get();
        }
        return null;
    }

    public <TParams> void addCar(ICarFactory<TParams> carFactory, TParams carParams) {
        // создаем автомобиль из переданной фабрики
        var car = carFactory.createCar(carParams, ++carNumberCounter);

        cars.add(car); // добавляем автомобиль
    }
}
