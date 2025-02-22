package zoo.domains;

import clinic.interfaces.Clinic;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * Класс, представляющий зоопарк.
 */
@Component
@ComponentScan("clinic")
@ComponentScan("zoo")
@NoArgsConstructor
public class Zoo {
    @Autowired
    Clinic clinic;

    @Getter
    List<Animal> animals = new ArrayList<>();

    @Getter
    List<Thing> things = new ArrayList<>();

    /**
     * Проверяет здоровье животного и если оно здорово, добавляет его в зоопарк.
     *
     * @param animal животное для добавления
     * @return true, если животное было добавлено
     */
    public boolean addAnimal(Animal animal) {
        if (!clinic.checkAnimal(animal)) {
            return false;
        }
        animals.add(animal);
        return true;
    }

    /**
     * Добавляет вещь в зоопарк.
     *
     * @param thing вещь для добавления
     */
    public void addThing(Thing thing) {
        things.add(thing);
    }
}
