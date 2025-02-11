package hse.kpo.factories;

import hse.kpo.domains.Car;
import hse.kpo.domains.HandEngine;
import hse.kpo.interfaces.CarFactory;
import hse.kpo.params.EmptyEngineParams;
import org.springframework.stereotype.Component;

/**
 * Класс для порождения автомобилей с ручным двигателем.
 */
@Component
public class HandCarFactory implements CarFactory<EmptyEngineParams> {

    /**
     * Создаёт автомобиль с ручным двигателем.
     *
     * @param carParams параметры двигателя
     * @param carNumber уникальный идентификатор автомобиля
     * @return новый автомобиль
     */
    @Override
    public Car createCar(EmptyEngineParams carParams, int carNumber) {
        var engine = new HandEngine();

        return new Car(carNumber, engine);
    }
}
