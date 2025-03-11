package fm.menus.impl;

import static java.lang.System.exit;

import fm.ConsoleApplication;
import fm.helpers.ConsoleReader;
import fm.menus.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("mainMenu")
public class MainMenu implements Menu {
    @Autowired
    public ConsoleApplication app;

    @Autowired
    ConsoleReader reader;

    @Override
    public void print() {
        var out = reader.getOutput();
        out.println("        \033[1;34mMain menu\033[0m");
        out.println("Available actions:");
        out.println("    1. Add objects");
        out.println("    2. Remove objects");
        out.println("    3. Change objects");
        out.println("    4. Print objects");
        out.println("    5. Analysis module");
        out.println("    6. Export data");
        out.println("    7. Exit");
    }

    @Override
    public void logic() {
        var action = reader.readInt("Enter number", 1, 7);
        if (action == 7) {
            exit(0);
        } else if (action == 1) {
            app.switchState("addMenu");
        } else if (action == 2) {
            app.switchState("removeMenu");
        } else if (action == 3) {
            app.switchState("changeMenu");
        } else if (action == 4) {
            app.switchState("printMenu");
        } else if (action == 5) {
            app.switchState("analysisMenu");
        } else if (action == 6) {
            app.switchState("exportMenu");
        }
    }
}
