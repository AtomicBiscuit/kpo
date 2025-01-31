package studying.factories;

import studying.domains.Car;
import studying.domains.LevitateEngine;
import studying.interfaces.ICarFactory;
import studying.params.EmptyEngineParams;

/**
 Класс для порождения летающих автомобилей
 */
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
