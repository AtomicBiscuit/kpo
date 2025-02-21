package hse.kpo.interfaces.cars;

import hse.kpo.domains.Car;

/**
 * Интерфейс для определения методов фабрик.
 *
 * @param <ParamsT> параметры для фабрик
 */
public interface CarFactory<ParamsT> {
    /**
     * Метод создания машин.
     *
     * @param carParams параметры для создания
     * @param carNumber номер
     * @return {@link Car}
     */
    Car create(ParamsT carParams, int carNumber);
}
