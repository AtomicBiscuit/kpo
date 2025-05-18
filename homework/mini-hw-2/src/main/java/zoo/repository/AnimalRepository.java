package zoo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import zoo.domain.animals.Animal;
import zoo.domain.animals.AnimalProvider;

/**
 * Unit-of-work для работы с таблицей животных.
 */
public interface AnimalRepository extends JpaRepository<Animal, Integer>, AnimalProvider {
    @Query("""
            SELECT a FROM Animal a
            """)
    @Override
    List<Animal> getAnimals();

    @Query("""
            SELECT a FROM Animal a
                     WHERE a.id = :id
            """)
    @Override
    Animal getAnimalById(Integer id);

    @Override
    Animal save(Animal animal);
}
