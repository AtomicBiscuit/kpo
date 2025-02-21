package hse.kpo.domains;

import hse.kpo.interfaces.Engine;
import hse.kpo.enums.ProductionTypes;
import lombok.ToString;

/**
 * Класс, реализующий {@link Engine} ручного типа.
 */
@ToString
public class HandEngine implements Engine {

    /**
     * Определяет совместимость покупателя и двигателя.
     *
     * @param customer покупатель
     * @param type     тип продукта, использующего двигать
     * @return true, если клиенту хватает силы рук
     */
    @Override
    public boolean isCompatible(Customer customer, ProductionTypes type) {
        return switch (type) {
            case ProductionTypes.CAR -> customer.getHandPower() > 5;
            case ProductionTypes.CATAMARAN -> customer.getHandPower() > 2;
            case null, default -> throw new RuntimeException("This type of production doesn't exist");
        };
    }
}
