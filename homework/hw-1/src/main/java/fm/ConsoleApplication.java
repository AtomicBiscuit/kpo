package fm;

import fm.menus.Menu;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class ConsoleApplication {
    @Autowired
    @Lazy
    @Qualifier("mainMenu")
    Menu menu;


    @Autowired
    @Lazy
    Map<String, Menu> states;

    public void switchState(String newState) {
        menu = states.get(newState);
        if (Objects.isNull(menu)) {
            throw new RuntimeException("Unknown state: " + newState);
        }
    }

    public void run() {
        while (true) {
            menu.print();
            menu.logic();
        }
    }
}
