package fm.facade;

import static fm.formater.PrettyText.format;

import fm.domains.Operation;
import fm.domains.types.Identifier;
import fm.factories.CategoryFactory;
import fm.filter.OperationFilter;
import fm.formater.Format;
import fm.helpers.ConsoleHelper;
import fm.params.CategoryParams;
import fm.storages.CategoryStorage;
import fm.storages.OperationStorage;
import java.io.PrintStream;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryFacade {
    CategoryStorage categoryStorage;

    OperationStorage operationStorage;

    CategoryFactory factory;

    @Setter
    CategoryParams params;

    ConsoleHelper helper;

    int num = 1;

    @Autowired
    public CategoryFacade(CategoryStorage categoryStorage, OperationStorage operationStorage, CategoryFactory factory
            , CategoryParams params, ConsoleHelper helper) {
        this.categoryStorage = categoryStorage;
        this.operationStorage = operationStorage;
        this.factory = factory;
        this.params = params;
        this.helper = helper;
    }

    public void printAll(PrintStream writer) {
        writer.println(format("Categories: ", Format.CYAN));
        categoryStorage.getAllCategories().forEach(category -> writer.println("  " + category));
        writer.println(format("End", Format.CYAN));
    }

    public int createCategory() {
        var account = factory.createCategory(params, new Identifier(num++));
        categoryStorage.addCategory(account);
        return num - 1;
    }

    public void removeCategory(Identifier id) {
        var cascadeOperations = OperationFilter.builder(operationStorage.getAllOperations())
                                               .filterByCategory(id)
                                               .build();
        cascadeOperations.stream().map(Operation::getId).forEach(operationStorage::removeOperation);
        categoryStorage.removeCategory(id);
    }

    public boolean changeCategory(Identifier id) {
        var rawCategory = categoryStorage.getCategory(id);
        if (rawCategory.isEmpty()) {
            helper.getOutput()
                  .println(format("Category with id " + id + " not found", Format.RED, Format.BOLD, Format.UNDERLINE));
            return false;
        }
        helper.getOutput().println("Now enter new data");
        var category = rawCategory.get();
        category.setName(params.getName());
        category.setType(params.getType());
        return true;
    }
}
