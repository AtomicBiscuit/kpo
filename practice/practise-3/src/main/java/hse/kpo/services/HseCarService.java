package hse.kpo.services;

import lombok.RequiredArgsConstructor;
import hse.kpo.interfaces.ICarProvider;
import hse.kpo.interfaces.ICustomerProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 Класс, представляющий систему для учёта клиентов и произведённых на продажу автомобилей.
 */
@Component
@RequiredArgsConstructor
public class HseCarService {

    @Autowired
    private final ICarProvider carProvider;

    @Autowired
    private final ICustomerProvider customerProvider;

    /**
     Присваивает пользователям в хранилище продающиеся автомобили, если они совместимы.
     */
    public void sellCars() {
        var customers = customerProvider.getCustomers();

        customers.stream().filter(customer -> Objects.isNull(customer.getCar()))
                 .forEach(customer -> {
                     var car = carProvider.takeCar(customer);
                     if (Objects.nonNull(car)) {
                         customer.setCar(car);
                     }
                 });
    }
}