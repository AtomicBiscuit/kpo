package hse.kpo.domains;

import lombok.ToString;
import hse.kpo.interfaces.IEngine;

/**
 Класс, представляющий двигатель на ручной тяге
 */
@ToString
public class HandEngine implements IEngine {

    /**
     Определяет совместимость покупателя и двигателя

     @param customer покупатель
     @return true, если клиенту хватает силы рук
     */
    @Override
    public boolean isCompatible(Customer customer) {
        return customer.getHandPower() > 5;
    }
}
