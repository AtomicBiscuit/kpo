package hse.kpo.interfaces;

import hse.kpo.domains.Customer;

/**
 * Интерфейс двигателя.
 */
public interface Engine {

    /**
     * Определяет совместимость покупателя и двигателя.
     *
     * @param customer покупатель
     * @return true, если двигатель подходит покупателю
     */
    boolean isCompatible(Customer customer);
}
