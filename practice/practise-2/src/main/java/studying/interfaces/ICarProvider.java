package studying.interfaces;

import studying.domains.Car;
import studying.domains.Customer;

/**
 Интерфейс для хранилища автомобилей
 */
public interface ICarProvider {

    /**
     Извлекает автомобиль, который подойдёт покупателю, из хранилища

     @param customer покупатель
     @return подходящий автомобиль, либо null если такого не нашлось
     */
    Car takeCar(Customer customer);
}
