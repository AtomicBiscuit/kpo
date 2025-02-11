package hse.kpo.domains;

import hse.kpo.interfaces.Engine;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Класс, представляющий автомобиль.
 */
@ToString
@AllArgsConstructor
public class Car {
    @Getter
    private int vin;

    private Engine engine;

    /**
     * Определяет совместимость покупателя и автомобиля.
     *
     * @param customer покупатель
     * @return true, если покупатель может использовать машину
     */
    public boolean isCompatible(Customer customer) {
        return this.engine.isCompatible(customer);
    }
}
