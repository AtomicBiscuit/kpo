package hse.kpo.domains;

import hse.kpo.interfaces.Engine;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Класс, представляющий катамаран.
 */
@ToString
@AllArgsConstructor
public class Catamaran {
    @Getter
    private int vin;

    private Engine engine;

    /**
     * Определяет совместимость покупателя и катамарана.
     *
     * @param customer покупатель
     * @return true, если покупатель может использовать катамаран
     */
    public boolean isCompatible(Customer customer) {
        return this.engine.isCompatible(customer);
    }
}
