package studying.domains;

import lombok.ToString;
import studying.interfaces.IEngine;

/**
 Класс, представляющий двигатель для левитирующих автомобилей
 */
@ToString
public class LevitateEngine implements IEngine {

    /**
     Определяет совместимость покупателя и двигателя

     @param customer покупатель
     @return true, если клиенту хватит iq
     */
    @Override
    public boolean isCompatible(Customer customer) {
        return customer.getIq() > 300;
    }
}
