package zoo.domain.enclosures;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Фабрика для порождения вольеров.
 */
@Component
@NoArgsConstructor
public class EnclosureFactory {
    public Enclosure createEnclosure(String type, Integer size, Integer capacity) {
        return new Enclosure(type, size, capacity, 0);
    }
}
