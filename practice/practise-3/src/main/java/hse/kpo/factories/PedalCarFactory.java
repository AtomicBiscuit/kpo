package hse.kpo.factories;

import hse.kpo.domains.Car;
import hse.kpo.domains.PedalEngine;
import hse.kpo.interfaces.CarFactory;
import hse.kpo.params.PedalEngineParams;
import org.springframework.stereotype.Component;

/**
 * Класс для порождения автомобилей с педальным двигателем.
 */
@Component
public class PedalCarFactory implements CarFactory<PedalEngineParams> {

    /**
     * Создаёт автомобиль с педальным двигателем.
     *
     * @param carParams параметры двигателя
     * @param carNumber уникальный идентификатор автомобиля
     * @return новый автомобиль
     */
    @Override
    public Car createCar(PedalEngineParams carParams, int carNumber) {
        var engine = new PedalEngine(carParams.pedalSize());

        return new Car(carNumber, engine);
    }
}
