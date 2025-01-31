package hse.kpo.services;

import lombok.RequiredArgsConstructor;
import hse.kpo.interfaces.ICarProvider;
import hse.kpo.interfaces.ICustomerProvider;

import java.util.Objects;

/**
 Класс, представляющий систему для учёта клиентов и произведённых на продажу автомобилей.
 */
@RequiredArgsConstructor
public class HseCarService {

    private final ICarProvider carProvider;

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