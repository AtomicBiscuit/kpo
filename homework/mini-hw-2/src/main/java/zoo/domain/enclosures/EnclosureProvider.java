package zoo.domain.enclosures;

import java.util.List;

/**
 * Интерфейс для поставщиков.
 */
public interface EnclosureProvider {
    public List<Enclosure> getEnclosures();

    public Enclosure getEnclosureById(Integer id);

    public Enclosure save(Enclosure enclosure);

    public void deleteById(Integer enclosureId);
}
