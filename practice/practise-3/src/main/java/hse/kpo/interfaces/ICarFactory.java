package hse.kpo.interfaces;

import hse.kpo.domains.Car;

/**
 Интерфейс для фабрик, порождающих автомобили
 */
public interface ICarFactory<TParams> {

    /**
     Создаёт автомобиль

     @param carParams параметры двигателя
     @param carNumber уникальный идентификатор автомобиля
     @return новый автомобиль
     */
    Car createCar(TParams carParams, int carNumber);
}
