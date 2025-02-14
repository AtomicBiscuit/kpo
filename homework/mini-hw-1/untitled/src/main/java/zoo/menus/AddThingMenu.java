package zoo.menus;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import zoo.domains.Thing;
import zoo.factories.ThingConsoleFactory;
import zoo.helpers.StringHelper;
import zoo.interfaces.Application;
import zoo.interfaces.Menu;

/**
 * Класс для отрисовки и обработки логики меню добавления вещей.
 */
@Component("AddThingMenu")
public class AddThingMenu implements Menu {
    @Autowired
    @Lazy
    Application application;

    @Autowired
    StringHelper stringHelper;

    @Autowired
    ThingConsoleFactory thingFactory;

    /**
     * Выводит меню в консоль.
     */
    @Override
    public void print() {
        System.out.println("Choose a thing:");
        ThingConsoleFactory.predatorsList.forEach(name -> System.out.println("....Add a " + name));
    }

    /**
     * Добавляет выбранную пользователем вещь в зоопарк.
     */
    @Override
    public void doLogic() {
        Thing thing;
        do {
            String thingName = stringHelper.read("Enter item`s name").strip().toLowerCase();
            thing = thingFactory.create(thingName);
            if (Objects.nonNull(thing)) {
                break;
            }
            System.out.println("Get unknown item, try again");
        } while (true);
        application.getZoo().addThing(thing);
        System.out.println("Success!");
        application.changeMenu("MainMenu");
    }
}
