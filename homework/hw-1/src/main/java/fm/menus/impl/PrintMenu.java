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

@Component("printMenu")
@AllArgsConstructor
public class PrintMenu implements Menu {
    ConsoleApplication app;

    ConsoleHelper helper;

    BankAccountFacade bankAccountFacade;

    CategoryFacade categoryFacade;

    OperationFacade operationFacade;

    @Override
    public void print() {
        var out = helper.getOutput();
        out.println(format("        Print objects menu", Format.BLUE, Format.BOLD));
        out.println("Available actions:");
        out.println("    1. Print all bank accounts");
        out.println("    2. Print all categories");
        out.println("    3. Print all operations");
        out.println("    0. Back to main menu");
    }

    @Override
    public void logic() {
        var action = helper.readInt("Enter number", 0, 3);
        if (action == 0) {
            app.switchState("mainMenu");
        } else if (action == 1) {
            bankAccountFacade.printAll(helper.getOutput());
        } else if (action == 2) {
            categoryFacade.printAll(helper.getOutput());
        } else if (action == 3) {
            operationFacade.printAll(helper.getOutput());
        }
    }
}
