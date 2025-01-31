package studying.interfaces;

import studying.domains.Customer;

/**
 Интерфейс двигателя
 */
public interface IEngine {

    /**
     Определяет совместимость покупателя и двигателя

     @param customer покупатель
     @return true, если двигатель подходит покупателю
     */
    boolean isCompatible(Customer customer);
}
