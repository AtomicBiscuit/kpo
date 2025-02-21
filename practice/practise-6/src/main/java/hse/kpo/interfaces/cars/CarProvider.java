package hse.kpo.interfaces.cars;

import hse.kpo.domains.Car;
import hse.kpo.domains.Customer;

/**
 * Интерфейс поставщика автомобилей.
 */
public interface CarProvider {

    /**
     * Метод покупки машины.
     *
     * @param customer - покупатель
     * @return - {@link Car}
     */
    Car takeCar(Customer customer);
}
