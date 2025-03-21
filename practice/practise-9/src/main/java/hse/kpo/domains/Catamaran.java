package hse.kpo.domains;

import hse.kpo.enums.EngineTypes;
import hse.kpo.enums.ProductionTypes;
import hse.kpo.interfaces.Engine;
import hse.kpo.interfaces.Transport;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Класс хранящий информацию о катамаране.
 */
@ToString
@Setter
@Getter
public class Catamaran implements Transport {
    private Engine engine;

    private int vin;

    public Catamaran(int vin, Engine engine) {
        this.vin = vin;
        this.engine = engine;
    }

    public boolean isCompatible(Customer customer) {
        return this.engine.isCompatible(customer, ProductionTypes.CATAMARAN);
    }

    public String getEngineType() {
        if (engine instanceof HandEngine) {
            return EngineTypes.HAND.name();
        }
        if (engine instanceof PedalEngine) {
            return EngineTypes.PEDAL.name();
        }
        if (engine instanceof LevitationEngine) {
            return EngineTypes.LEVITATION.name();
        }
        throw new RuntimeException("Where is engine???");
    }

    @Override
    public String getTransportType() {
        return ProductionTypes.CATAMARAN.name();
    }
}
