package hse.kpo.services.customers;

import hse.kpo.domains.Customer;
import hse.kpo.interfaces.CustomerProvider;
import hse.kpo.repositories.CustomerRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Хранилище информации о пользователях.
 */
@RequiredArgsConstructor
@Component
public class CustomerService implements CustomerProvider {
    private final CustomerRepository customerRepository;

    @Override
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    /**
     * Метод добавления покупателя в систему.
     *
     * @param customer покупатель
     */
    public void addCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public boolean updateCustomer(Customer updatedCustomer) {
        if (customerRepository.existsById(updatedCustomer.getId())) {
            customerRepository.save(updatedCustomer);
            return true;
        }
        return false;
    }

    public boolean deleteCustomer(String name) {
        customerRepository.deleteByName(name);
        return true;
    }
}
