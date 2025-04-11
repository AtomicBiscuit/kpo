package hse.kpo.domains;

import hse.kpo.enums.EngineTypes;
import hse.kpo.enums.ProductionTypes;
import hse.kpo.interfaces.Transport;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

/**
 * Класс хранящий информацию о катамаране.
 */
@Getter
@ToString
@Entity
@Table(name = "catamarans")
public class Catamaran implements Transport {
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "engine_id")
    private final AbstractEngine engine;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int vin;

    public Catamaran(int vin, AbstractEngine engine) {
        this.vin = vin;
        this.engine = engine;
    }

    public Catamaran(AbstractEngine engine) {
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
