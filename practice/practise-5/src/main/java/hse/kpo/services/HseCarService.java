package hse.kpo.services;

import hse.kpo.interfaces.CarProvider;
import hse.kpo.interfaces.CustomerProvider;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Класс, представляющий систему для учёта клиентов и произведённых на продажу автомобилей.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class HseCarService {

    @Autowired
    private final CarProvider carProvider;

    @Autowired
    private final CustomerProvider customerProvider;

    var customers = customerProvider.getCustomers();

    /**
     * Присваивает пользователям в хранилище продающиеся автомобили, если они совместимы.
     */
    public void sellCars() {
        var customers = customerProvider.getCustomers();

        customers.stream().filter(customer -> Objects.isNull(customer.getCar()))
                 .forEach(customer -> {
                     var car = carProvider.takeCar(customer);
                     if (Objects.nonNull(car)) {
                         customer.setCar(car);
                     } else {
                         log.warn("No car in CarService");
                     }
                 });
    }
}