package hse.kpo.domains;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import hse.kpo.interfaces.IEngine;

/**
 Класс, представляющий автомобиль
 */
@ToString
@AllArgsConstructor
public class Car {
    @Getter
    private int VIN;

    private IEngine engine;

    /**
     Определяет совместимость покупателя и автомобиля

     @param customer покупатель
     @return true, если использовать покупатель может машину
     */
    public boolean isCompatible(Customer customer) {
        return this.engine.isCompatible(customer);
    }
}
