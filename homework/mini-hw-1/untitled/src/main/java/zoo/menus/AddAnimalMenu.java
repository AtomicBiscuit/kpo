package zoo.menus;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import zoo.domains.Animal;
import zoo.factories.HerboFactory;
import zoo.factories.PredatorFactory;
import zoo.helpers.StringHelper;
import zoo.interfaces.Application;
import zoo.interfaces.Menu;

/**
 * Класс для отрисовки и обработки логики меню добавления животных.
 */
@Component("AddAnimalMenu")
public class AddAnimalMenu implements Menu {
    @Autowired
    @Lazy
    Application application;

    @Autowired
    StringHelper stringHelper;

    @Autowired
    HerboFactory herboFactory;

    @Autowired
    PredatorFactory predatorFactory;

    public static List<String> names = Stream.concat(HerboFactory.herbosList.stream(),
                                                     PredatorFactory.predatorsList.stream()).toList();

    /**
     * Выводит главное меню в консоль.
     */
    @Override
    public void print() {
        System.out.println("Choose an animal:");
        names.forEach(name -> System.out.println("....Add a " + name));
    }

    /**
     * Добавляет выбранное животное в зоопарк.
     */
    @Override
    public void doLogic() {
        Animal animal;
        do {
            String animalName = stringHelper.read("Enter an animal name").strip().toLowerCase();
            animal = herboFactory.create(animalName);
            if (Objects.isNull(animal)) {
                animal = predatorFactory.create(animalName);
            }
            if (Objects.nonNull(animal)) {
                break;
            }
            System.out.println("Get unknown animal, try again");
        } while (true);
        boolean state = application.getZoo().addAnimal(animal);
        System.out.println(state ? "Success!" : "Unfortunately, the animal is sick! It won`t be added");
        application.changeMenu("MainMenu");
    }
}
