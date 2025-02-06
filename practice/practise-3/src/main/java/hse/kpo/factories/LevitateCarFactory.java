package hse.kpo.factories;

import hse.kpo.domains.Car;
import hse.kpo.domains.LevitateEngine;
import hse.kpo.interfaces.ICarFactory;
import hse.kpo.params.EmptyEngineParams;
import org.springframework.stereotype.Component;

/**
 Класс для порождения летающих автомобилей
 */
@Component
public class LevitateCarFactory implements ICarFactory<EmptyEngineParams> {

    /**
     Создаёт автомобиль с левитирующим двигателем

     @param carParams параметры двигателя
     @param carNumber уникальный идентификатор автомобиля
     @return новый автомобиль
     */
    @Override
    public Car createCar(EmptyEngineParams carParams, int carNumber) {
        var engine = new LevitateEngine();

        return new Car(carNumber, engine);
    }
}
