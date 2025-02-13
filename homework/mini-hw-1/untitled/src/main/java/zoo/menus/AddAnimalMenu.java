package zoo.menus;

import static java.lang.System.exit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import zoo.ZooApplication;
import zoo.domains.Animal;
import zoo.domains.herbos.Herbo;
import zoo.factories.HerboConsoleFactory;
import zoo.factories.PredatorConsoleFactory;
import zoo.helpers.IntHelper;
import zoo.interfaces.Menu;

/**
 * Класс для отрисовки и обработки логики меню добавление животных.
 */
@Component("AddAnimalMenu")
public class AddAnimalMenu implements Menu {
    @Autowired
    @Lazy
    ZooApplication application;

    @Autowired
    IntHelper intHelper;

    @Autowired
    HerboConsoleFactory herboFactory;

    @Autowired
    PredatorConsoleFactory predatorFactory;

    /**
     * Выводит главное меню в консоль.
     */
    @Override
    public void print() {
        System.out.println("Choose an action:");
        System.out.println("    1.   Add a Monkey");
        System.out.println("    2.   Add a Rabbit");
        System.out.println("    3.   Add a Wolf");
        System.out.println("    4.   Add a Tiger");
    }

    /**
     * Добавляет выбранное животное в зоопарк.
     */
    @Override
    public void doLogic() {
        int action = intHelper.read("Enter a number", 1, 4);
        Animal animal = null;
        if (action == 1) {
            animal = herboFactory.createMonkey();
        } else if (action == 2) {
            animal = herboFactory.createRabbit();
        } else if (action == 3) {
            animal = predatorFactory.createWolf();
        } else {
            animal = predatorFactory.createTiger();
        }

        boolean state = application.getZoo().addAnimal(animal);
        if (state) {
            System.out.println("Success!");
        } else {
            System.out.println("Unfortunately, the animal is sick! It won`t be added");
        }
        application.changeMenu("MainMenu");
    }
}
