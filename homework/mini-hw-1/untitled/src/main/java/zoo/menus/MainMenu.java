package zoo.menus;

import static java.lang.System.exit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import zoo.ZooApplication;
import zoo.domains.herbos.Herbo;
import zoo.helpers.IntHelper;
import zoo.interfaces.Alive;
import zoo.interfaces.Menu;

/**
 * Класс для отрисовки и обработки логики главного меню приложения.
 */
@Component("MainMenu")
public class MainMenu implements Menu {
    @Autowired
    @Lazy
    ZooApplication application;

    @Autowired
    IntHelper intHelper;

    /**
     * Выводит главное меню в консоль.
     */
    @Override
    public void print() {
        System.out.println("Choose an action:");
        System.out.println("    1.   Add new animal");
        System.out.println("    2.   Add new thing");
        System.out.println("    3.   Get report");
        System.out.println("    4.   Get daily food consumption");
        System.out.println("    5.   Get friendly animals");
        System.out.println("    6.   Exit");
    }

    /**
     * Выводит суточное потребление пищи.
     */
    void printDailyFoodConsumption() {
        var sum = application.getZoo().getAnimals().stream().mapToInt(Alive::getFood).sum();
        System.out.println("Daily consumption is " + sum);
    }

    /**
     * Выводит список дружелюбных животных.
     */
    void printFriendlyAnimals() {
        System.out.println("Friendly animals are:");
        application.getZoo().getAnimals().stream().filter(Herbo.class::isInstance).map(Herbo.class::cast)
                   .filter(herbo -> herbo.getKindness() > 5).forEach(System.out::println);
    }

    /**
     * Выводит список всех животных.
     */
    void printAllAnimals() {
        System.out.println("List of all animals:");
        application.getZoo().getAnimals().forEach(System.out::println);
    }

    /**
     * Выводит список всех инвентаризованных вещей.
     */
    void printAllThings() {
        System.out.println("List of all things:");
        application.getZoo().getThings().forEach(System.out::println);
    }

    /**
     * Переводит пользователя в выбранное подменю.
     */
    @Override
    public void doLogic() {
        int action = intHelper.read("Enter a number", 1, 6);
        if (action == 6) {
            exit(0);
        } else if (action == 5) {
            printFriendlyAnimals();
        } else if (action == 4) {
            printDailyFoodConsumption();
        } else if (action == 3) {
            printAllAnimals();
            printAllThings();
        } else if (action == 2) {
            application.changeMenu("AddThingMenu");
        } else if (action == 1) {
            application.changeMenu("AddAnimalMenu");
        }
    }
}
