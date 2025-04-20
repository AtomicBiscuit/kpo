package zoo.repository;

import java.util.Collections;
import java.util.List;
import org.hibernate.annotations.processing.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import zoo.domain.enclosures.Enclosure;
import zoo.domain.enclosures.EnclosureProvider;

/**
 * Unit-of-work для работы с таблицей вольеров.
 */
public interface EnclosureRepository extends JpaRepository<Enclosure, Integer>, EnclosureProvider {
    @SQL("""
            SELECT * FROM enclosure
            """)
    @Override
    default List<Enclosure> getEnclosures() {
        return Collections.emptyList();
    }

    @SQL("""
            SELECT * FROM enclosure
                     WHERE id = :id
                     LIMIT 1
            """)
    @Override
    Enclosure getEnclosureById(Integer id);
}
