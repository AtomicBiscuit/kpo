package studying.factories;

import studying.domains.Car;
import studying.domains.HandEngine;
import studying.interfaces.ICarFactory;
import studying.params.EmptyEngineParams;

/**
 Класс для порождения автомобилей с ручным двигателе
 */
public class HandCarFactory implements ICarFactory<EmptyEngineParams> {

    /**
     Создаёт автомобиль с ручным двигателем

     @param carParams параметры двигателя
     @param carNumber уникальный идентификатор автомобиля
     @return новый автомобиль
     */
    @Override
    public Car createCar(EmptyEngineParams carParams, int carNumber) {
        var engine = new HandEngine();

        return new Car(carNumber, engine);
    }
}
