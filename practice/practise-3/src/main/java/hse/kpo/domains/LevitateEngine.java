package hse.kpo.domains;

import hse.kpo.interfaces.Engine;
import lombok.ToString;

/**
 * Класс, представляющий двигатель для левитирующих автомобилей.
 */
@ToString
public class LevitateEngine implements Engine {

    /**
     * Определяет совместимость покупателя и двигателя.
     *
     * @param customer покупатель
     * @return true, если клиенту хватит iq
     */
    @Override
    public boolean isCompatible(Customer customer) {
        return customer.getIq() > 300;
    }
}
