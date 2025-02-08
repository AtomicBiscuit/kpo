package hse.kpo.domains;

import hse.kpo.interfaces.Engine;
import lombok.ToString;

/**
 * Класс, представляющий двигатель на ручной тяге.
 */
@ToString
public class HandEngine implements Engine {

    /**
     * Определяет совместимость покупателя и двигателя.
     *
     * @param customer покупатель
     * @return true, если клиенту хватает силы рук
     */
    @Override
    public boolean isCompatible(Customer customer) {
        return customer.getHandPower() > 5;
    }
}
