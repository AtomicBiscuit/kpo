package studying.interfaces;

import studying.domains.Customer;

import java.util.List;

/**
 Интерфейс для хранилища покупателей
 */
public interface ICustomerProvider {
    /**
     Возвращает неизменяемый список всех пользователей

     @return список пользователей
     */
    List<Customer> getCustomers();
}
