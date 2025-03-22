package hse.kpo.domains.cars;

import hse.kpo.domains.Customer;
import hse.kpo.enums.ProductionTypes;
import hse.kpo.interfaces.Engine;
import hse.kpo.interfaces.Transport;
import lombok.Getter;
import lombok.ToString;

/**
 * Класс хранящий информацию о машине.
 */
@ToString
public class Car implements Transport {

    private Engine engine;

    @Getter
    private int vin;

    public Car(int vin, Engine engine) {
        this.vin = vin;
        this.engine = engine;
    }

    public boolean isCompatible(Customer customer) {
        return this.engine.isCompatible(customer, ProductionTypes.CAR);
    }

    @Override
    public String getEngineType() {
        return engine.getType();
    }

    @Override
    public String getTransportType() {
        return "Car";
    }
}
