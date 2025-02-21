package hse.kpo.domains;

import hse.kpo.interfaces.Engine;
import hse.kpo.enums.ProductionTypes;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;


/**
 * Класс, реализующий {@link IEngine} педального типа.
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
     * @param type     тип продукции, использующей двигатель
     * @return true, если клиенту хватает силы ног
     */
    @Override
    public boolean isCompatible(Customer customer, ProductionTypes type) {
        return switch (type) {
            case ProductionTypes.CAR -> customer.getLegPower() > 5;
            case ProductionTypes.CATAMARAN -> customer.getLegPower() > 2;
            case null, default -> throw new RuntimeException("This type of production doesn't exist");
        };
    }
}
