package zoo.menus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import zoo.ZooApplication;
import zoo.domains.Thing;
import zoo.factories.HerboConsoleFactory;
import zoo.factories.PredatorConsoleFactory;
import zoo.factories.ThingConsoleFactory;
import zoo.helpers.IntHelper;
import zoo.interfaces.Menu;

/**
 * Класс для отрисовки и обработки логики меню добавление животных.
 */
@Component("AddThingMenu")
public class AddThingMenu implements Menu {
    @Autowired
    @Lazy
    ZooApplication application;

    @Autowired
    IntHelper intHelper;

    @Autowired
    HerboConsoleFactory herboFactory;

    @Autowired
    PredatorConsoleFactory predatorFactory;


    @Autowired
    ThingConsoleFactory thingFactory;

    /**
     * Выводит главное меню в консоль.
     */
    @Override
    public void print() {
        System.out.println("Choose an action:");
        System.out.println("    1.   Add a Computer");
        System.out.println("    2.   Add a Table");
    }

    /**
     * Добавляет выбранную вещь в зоопарк.
     */
    @Override
    public void doLogic() {
        int action = intHelper.read("Enter a number", 1, 2);
        Thing thing = null;
        if (action == 1) {
            thing = thingFactory.createComputer();
        } else {
            thing = thingFactory.createTable();
        }
        application.getZoo().addThing(thing);
        System.out.println("Success!");
        application.changeMenu("MainMenu");
    }
}
