package hse.kpo.services;

import hse.kpo.domains.Customer;
import hse.kpo.interfaces.CustomerProvider;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * Класс-хранилище пользователей.
 */
@Component
public class CustomerStorage implements CustomerProvider {
    private final List<Customer> customers = new ArrayList<>();

    /**
     * Возвращает список всех пользователей.
     *
     * @return список пользователей
     */
    @Override
    public List<Customer> getCustomers() {
        return customers;
    }

    /**
     * Добавляет пользователя в хранилище.
     *
     * @param customer пользователь для добавления
     */
    public void addCustomer(Customer customer) {
        customers.add(customer); // просто добавляем покупателя в список
    }
}
