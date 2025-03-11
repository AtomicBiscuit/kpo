package fm.menus.impl;

import static fm.formater.PrettyText.format;

import fm.ConsoleApplication;
import fm.domains.types.Identifier;
import fm.facade.BankAccountFacade;
import fm.facade.CategoryFacade;
import fm.facade.OperationFacade;
import fm.formater.Format;
import fm.helpers.ConsoleHelper;
import fm.menus.Menu;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component("removeMenu")
@AllArgsConstructor
public class RemoveMenu implements Menu {
    ConsoleApplication app;

    ConsoleHelper helper;

    BankAccountFacade bankAccountFacade;

    CategoryFacade categoryFacade;

    OperationFacade operationFacade;

    @Override
    public void print() {
        var out = helper.getOutput();
        out.println(format("        Remove object menu", Format.BLUE, Format.BOLD));
        out.println("Available actions:");
        out.println("    1. Delete bank account " + format("(Cascade)", Format.UNDERLINE));
        out.println("    2. Delete category " + format("(Cascade)", Format.UNDERLINE));
        out.println("    3. Delete operation");
        out.println("    0. Back to main menu");
    }

    @Override
    public void logic() {
        var action = helper.readInt("Enter number", 0, 3);
        if (action == 0) {
            app.switchState("mainMenu");
            return;
        } else if (action == 1) {
            var id = helper.readInt("Enter account id", 1, 1_000_000_000);
            bankAccountFacade.removeAccount(new Identifier(id));
        } else if (action == 2) {
            var id = helper.readInt("Enter category id", 1, 1_000_000_000);
            categoryFacade.removeCategory(new Identifier(id));
        } else if (action == 3) {
            var id = helper.readInt("Enter operation id", 1, 1_000_000_000);
            operationFacade.removeOperation(new Identifier(id));
        }
        helper.getOutput().println(format("Complete", Format.PURPLE));
    }
}
