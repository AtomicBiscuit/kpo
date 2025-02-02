package studying.domains;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import studying.interfaces.IEngine;


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
