package zoo.domain.animals;

import java.time.LocalDate;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Фабрика для порождения животных.
 */
@Component
@NoArgsConstructor
public class AnimalFactory {
    public Animal createAnimal(String type, String name, LocalDate birthday, AnimalSex sex, String favoriteFood,
                               Boolean healthy) {
        return new Animal(type, name, birthday, sex, favoriteFood, healthy);
    }
}
