package hse.kpo.domains;

import hse.kpo.interfaces.Engine;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;


/**
 * Класс, представляющий педальный двигатель.
 */
@ToString
@Getter
@RequiredArgsConstructor
public class PedalEngine implements Engine {
    private final int size;

    /**
     * Определяет совместимость покупателя и двигателя.
     *
     * @param customer покупатель
     * @return true, если клиенту хватает силы ног
     */
    @Override
    public boolean isCompatible(Customer customer) {
        return customer.getLegPower() > 5;
    }
}
