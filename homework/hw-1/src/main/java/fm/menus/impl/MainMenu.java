package fm.menus.impl;

import static fm.formater.PrettyText.format;
import static java.lang.System.exit;

import fm.ConsoleApplication;
import fm.formater.Format;
import fm.helpers.ConsoleHelper;
import fm.menus.Menu;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component("mainMenu")
public class MainMenu implements Menu {
    private ConsoleApplication app;

    private ConsoleHelper helper;

    @Override
    public void print() {
        var out = helper.getOutput();
        out.println(format("        Main menu", Format.BLUE, Format.BOLD));
        out.println("Available actions:");
        out.println("    1. Add objects");
        out.println("    2. Remove objects");
        out.println("    3. Change objects");
        out.println("    4. Print objects");
        out.println("    5. Analysis module");
        out.println("    6. Export data");
        out.println("    0. Exit");
    }

    @Override
    public void logic() {
        var action = helper.readInt("Enter number", 0, 6);
        if (action == 0) {
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
