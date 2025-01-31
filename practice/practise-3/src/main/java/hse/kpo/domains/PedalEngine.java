package hse.kpo.domains;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import hse.kpo.interfaces.IEngine;


/**
 Класс, представляющий педальный двигатель
 */
@ToString
@Getter
@RequiredArgsConstructor
public class PedalEngine implements IEngine {
    private final int size;

    /**
     Определяет совместимость покупателя и двигателя

     @param customer покупатель
     @return true, если клиенту хватает силы ног
     */
    @Override
    public boolean isCompatible(Customer customer) {
        return customer.getLegPower() > 5;
    }
}
