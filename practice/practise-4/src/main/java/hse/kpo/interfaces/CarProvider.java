package hse.kpo.interfaces;

import hse.kpo.domains.Car;
import hse.kpo.domains.Customer;

/**
 * Интерфейс для хранилища автомобилей.
 */
public interface CarProvider {

    /**
     * Извлекает автомобиль, который подойдёт покупателю, из хранилища.
     *
     * @param customer покупатель
     * @return подходящий автомобиль, либо null если такого не нашлось
     */
    Car takeCar(Customer customer);
}
