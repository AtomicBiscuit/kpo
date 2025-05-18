package zoo.domain.animals;

import java.util.List;

/**
 * Интерфейс для поставщиков.
 */
public interface AnimalProvider {
    public List<Animal> getAnimals();

    public Animal getAnimalById(Integer id);

    public Animal save(Animal animal);

    public void deleteById(Integer animalId);
}
