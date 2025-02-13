package zoo;

import java.util.Map;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import zoo.domains.Zoo;
import zoo.interfaces.Menu;

/**
 * Консольное приложение зоопарка.
 */
@ComponentScan("zoo")
public class ZooApplication {
    @Autowired
    @Getter
    Zoo zoo;

    @Autowired
    @Qualifier("MainMenu")
    Menu active;

    @Autowired
    Map<String, Menu> menus;

    /**
     * Заменяет активное меню на новое.
     *
     * @param newMenuName имя установленного меню
     */
    public void changeMenu(String newMenuName) {
        active = menus.get(newMenuName);
    }

    /**
     * Запускает бесконечный цикл работы приложения.
     */
    public void run() {
        while (true) {
            active.print();
            active.doLogic();
        }
    }
}
