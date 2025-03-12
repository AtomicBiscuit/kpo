package fm.menus.impl;

import static fm.formater.PrettyText.format;

import fm.ConsoleApplication;
import fm.domains.Category;
import fm.domains.types.Identifier;
import fm.formater.Format;
import fm.helpers.ConsoleHelper;
import fm.menus.Menu;
import fm.services.AnalysisService;
import fm.storages.CategoryStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component("analysisMenu")
@AllArgsConstructor
public class AnalysisMenu implements Menu {
    ConsoleApplication app;

    ConsoleHelper helper;

    CategoryStorage categoryStorage;

    AnalysisService analysisService;

    @Override
    public void print() {
        var out = helper.getOutput();
        out.println(format("        Analytic menu", Format.BLUE, Format.BOLD));
        out.println("Available actions:");
        out.println("    1. Calculate profit by period");
        out.println("    2. Calculate income and expense by all categories");
        out.println("    0. Back to main menu");
    }

    private void printDelta() {
        var id = helper.readInt("Enter account id", Identifier.MIN_ID, Identifier.MAX_ID);
        var from = helper.readDate("Enter period start date");
        var to = helper.readDate("Enter period end date");
        var delta = analysisService.getDelta(new Identifier(id), from, to);
        helper.getOutput().println(format("Calculated profit is ", Format.CYAN) + format("" + delta, Format.PURPLE));
    }

    private void printCategorySum(Category category) {
        var result = analysisService.getSumByCategory(category.getId());
        var categoryColored = format(category.getName(), Format.BOLD, result > 0 ? Format.GREEN : Format.RED);
        helper.getOutput().println("  " + categoryColored + ": " + format("" + result, Format.PURPLE));
    }

    @Override
    public void logic() {
        var action = helper.readInt("Enter number", 0, 3);
        if (action == 0) {
            app.switchState("mainMenu");
        } else if (action == 1) {
            printDelta();
        } else if (action == 2) {
            helper.getOutput().println(format("Categories: ", Format.CYAN));
            categoryStorage.getAllCategories().forEach(this::printCategorySum);
            helper.getOutput().println(format("End: ", Format.CYAN));
        }
    }
}
