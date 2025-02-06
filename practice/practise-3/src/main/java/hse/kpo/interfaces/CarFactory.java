package hse.kpo.interfaces;

import hse.kpo.domains.Car;

/**
 * Интерфейс для фабрик, порождающих автомобили.
 */
public interface CarFactory<ParamsT> {

    /**
     * Создаёт автомобиль.
     *
     * @param carParams параметры двигателя
     * @param carNumber уникальный идентификатор автомобиля
     * @return новый автомобиль
     */
    Car createCar(ParamsT carParams, int carNumber);
}
