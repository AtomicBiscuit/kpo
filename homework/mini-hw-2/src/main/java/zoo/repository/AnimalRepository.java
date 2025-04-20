package zoo.repository;

import java.util.Collections;
import java.util.List;
import org.hibernate.annotations.processing.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import zoo.domain.animals.Animal;
import zoo.domain.animals.AnimalProvider;

/**
 * Unit-of-work для работы с таблицей животных.
 */
public interface AnimalRepository extends JpaRepository<Animal, Integer>, AnimalProvider {
    @SQL("""
            SELECT * FROM animal
            """)
    @Override
    default List<Animal> getAnimals() {
        return Collections.emptyList();
    }

    @SQL("""
            SELECT * FROM animal
                     WHERE id = :id
            """)
    @Override
    Animal getAnimalById(Integer id);

    @Override
    Animal save(Animal animal);
}
