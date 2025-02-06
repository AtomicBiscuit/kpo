package hse.kpo.services;

import static java.util.stream.IntStream.range;

import hse.kpo.domains.Customer;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CustomerStorageTests {
    @Autowired
    CustomerStorage customerStorage;

    @Test
    @DisplayName("Проверка корректности создания хранилища пользователей")
    void dependencyInjectionTest() {
        Assertions.assertNotNull(customerStorage);
    }

    @Test
    @DisplayName("Добавление пользователя")
    void addCustomerTest() {
        var customer = new Customer("Vladislav", 7, 3, 42);
        customerStorage.addCustomer(customer);

        Assertions.assertEquals(List.of(customer), customerStorage.getCustomers());
    }

    @Test
    @DisplayName("Добавление 10 пользователей")
    void addManyCustomersTest() {
        var customers = new ArrayList<Customer>();
        range(0, 10).forEach(number -> customers.add(new Customer("Anton " + number, 2, 3, 104)));

        customers.forEach(customer -> customerStorage.addCustomer(customer));

        Assertions.assertEquals(customers, customerStorage.getCustomers());
    }
}
