package zoo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import zoo.domain.enclosures.Enclosure;
import zoo.domain.enclosures.EnclosureProvider;

/**
 * Unit-of-work для работы с таблицей вольеров.
 */
public interface EnclosureRepository extends JpaRepository<Enclosure, Integer>, EnclosureProvider {
    @Query("""
            SELECT e FROM Enclosure e
            """)
    @Override
    List<Enclosure> getEnclosures();

    @Query("""
            SELECT e FROM Enclosure e
                     WHERE e.id = :id
            """)
    @Override
    Enclosure getEnclosureById(Integer id);
}
