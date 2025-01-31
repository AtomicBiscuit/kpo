package studying.factories;

import studying.domains.Car;
import studying.domains.PedalEngine;
import studying.interfaces.ICarFactory;
import studying.params.PedalEngineParams;

/**
 Класс для порождения автомобилей с педальным двигателе
 */
public class PedalCarFactory implements ICarFactory<PedalEngineParams> {

    /**
     Создаёт автомобиль с педальным двигателем

     @param carParams параметры двигателя
     @param carNumber уникальный идентификатор автомобиля
     @return новый автомобиль
     */
    @Override
    public Car createCar(PedalEngineParams carParams, int carNumber) {
        var engine = new PedalEngine(carParams.pedalSize());

        return new Car(carNumber, engine);
    }
}
