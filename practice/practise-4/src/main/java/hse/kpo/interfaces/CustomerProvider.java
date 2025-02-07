package hse.kpo.interfaces;

import hse.kpo.domains.Customer;
import java.util.List;

/**
 * Интерфейс для хранилища покупателей.
 */
public interface CustomerProvider {
    /**
     * Возвращает неизменяемый список всех пользователей.
     *
     * @return список пользователей
     */
    List<Customer> getCustomers();
}
