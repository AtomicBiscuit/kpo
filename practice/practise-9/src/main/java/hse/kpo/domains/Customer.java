package hse.kpo.domains;

import hse.kpo.domains.cars.Car;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Класс, описывающий покупателя.
 */
@Getter
@ToString
@Builder
@Setter
public class Customer {
    private String name;

    private int legPower;

    private int handPower;

    private int iq;

    private Car car;

    private Catamaran catamaran;
}
