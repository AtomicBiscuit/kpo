package hse.kpo.repos;

import hse.kpo.domains.Catamaran;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CatamaranRepository extends JpaRepository<Catamaran, Integer> {
    @Query("""
                SELECT c
                FROM Catamaran c
                JOIN c.engine e
                WHERE e.type = :engineType
            """)
    List<Catamaran> findCatamaranByEngine(@Param("engineType") String engineType);
}