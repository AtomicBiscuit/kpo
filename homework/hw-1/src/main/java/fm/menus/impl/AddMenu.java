package fm.menus.impl;

import static fm.formater.PrettyText.format;

import fm.ConsoleApplication;
import fm.facade.BankAccountFacade;
import fm.facade.CategoryFacade;
import fm.facade.OperationFacade;
import fm.formater.Format;
import fm.helpers.ConsoleHelper;
import fm.menus.Menu;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component("addMenu")
@AllArgsConstructor
public class AddMenu implements Menu {
    ConsoleApplication app;

    ConsoleHelper helper;

    BankAccountFacade bankAccountFacade;

    CategoryFacade categoryFacade;

    OperationFacade operationFacade;

    @Override
    public void print() {
        var out = helper.getOutput();
        out.println(format("        Create object menu", Format.BLUE, Format.BOLD));
        out.println("Available actions:");
        out.println("    1. Add bank account");
        out.println("    2. Add category");
        out.println("    3. Add operation");
        out.println("    0. Back to main menu");
    }

    @Override
    public void logic() {
        int id = 0;
        var action = helper.readInt("Enter number", 0, 3);
        if (action == 0) {
            app.switchState("mainMenu");
        } else if (action == 1) {
            id = bankAccountFacade.createAccount();
        } else if (action == 2) {
            id = categoryFacade.createCategory();
        } else if (action == 3) {
            id = operationFacade.createOperation();
        }
        if (id != 0) {
            var coloredId = format(String.valueOf(id), Format.BOLD, Format.PURPLE);
            helper.getOutput().println(format("New object with id ", Format.GREEN) + coloredId + format(" created", Format.GREEN));
        }
    }
}
