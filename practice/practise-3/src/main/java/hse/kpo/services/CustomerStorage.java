package hse.kpo.services;

import hse.kpo.domains.Customer;
import hse.kpo.interfaces.ICustomerProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 Класс-хранилище пользователей
 */
@Component
public class CustomerStorage implements ICustomerProvider {
    private List<Customer> customers = new ArrayList<>();

    /**
     Возвращает список всех пользователей

     @return список пользователей
     */
    @Override
    public List<Customer> getCustomers() {
        return customers;
    }

    /**
     Добавляет пользователя в хранилище

     @param customer пользователь для добавления
     */
    public void addCustomer(Customer customer) {
        customers.add(customer); // просто добавляем покупателя в список
    }
}
