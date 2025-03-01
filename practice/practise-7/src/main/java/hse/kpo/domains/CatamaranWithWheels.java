package hse.kpo.domains;

import hse.kpo.enums.ProductionTypes;
import lombok.ToString;

/**
 * Класс хранящий информацию о катамаране с колёсами.
 */
@ToString
public class CatamaranWithWheels extends Car {
    private final Catamaran catamaran;

    public CatamaranWithWheels(Catamaran catamaran, int carNumber) {
        super(carNumber, catamaran.getEngine());
        this.catamaran = catamaran;
    }

    @Override
    public boolean isCompatible(Customer customer) {
        return this.catamaran.getEngine().isCompatible(customer, ProductionTypes.CAR);
    }
}
