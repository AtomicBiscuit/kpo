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

@Component("changeMenu")
@AllArgsConstructor
public class ChangeMenu implements Menu {
    ConsoleApplication app;

    ConsoleHelper helper;

    BankAccountFacade bankAccountFacade;

    CategoryFacade categoryFacade;

    OperationFacade operationFacade;

    @Override
    public void print() {
        var out = helper.getOutput();
        out.println(format("        Change object menu", Format.BLUE, Format.BOLD));
        out.println("Available actions:");
        out.println("    1. Change bank account");
        out.println("    2. Change category");
        out.println("    3. Change operation");
        out.println("    0. Back to main menu");
    }

    @Override
    public void logic() {
        var action = helper.readInt("Enter number", 0, 3);
        boolean flag = false;
        if (action == 0) {
            app.switchState("mainMenu");
            return;
        } else if (action == 1) {
            var id = helper.readInt("Enter account id", Identifier.MIN_ID, Identifier.MAX_ID);
            flag = bankAccountFacade.changeAccount(new Identifier(id));
        } else if (action == 2) {
            var id = helper.readInt("Enter category id", Identifier.MIN_ID, Identifier.MAX_ID);
            flag = categoryFacade.changeCategory(new Identifier(id));
        } else if (action == 3) {
            var id = helper.readInt("Enter operation id", Identifier.MIN_ID, Identifier.MAX_ID);
            flag = operationFacade.changeOperation(new Identifier(id));
        }
        if (flag) {
            helper.getOutput().println(format("Complete", Format.PURPLE));
        }
    }
}
