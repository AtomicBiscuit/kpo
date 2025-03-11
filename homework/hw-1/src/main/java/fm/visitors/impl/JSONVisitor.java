package fm.visitors.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import fm.domains.BankAccount;
import fm.domains.Category;
import fm.domains.Operation;
import fm.visitors.Visitor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import org.springframework.stereotype.Component;

/**
 * Посетитель для записи данных в JSON формате.
 */
@Getter
@Component
public class JSONVisitor implements Visitor {
    private Map<String, List<Object>> objects;

    JSONVisitor() {
        clear();
    }

    @Override
    public void visit(BankAccount account) {
        objects.get("BankAccount").add(account);
    }

    @Override
    public void visit(Category category) {
        objects.get("Category").add(category);
    }

    @Override
    public void visit(Operation operation) {
        objects.get("Operation").add(operation);
    }

    private void clear() {
        objects = Map.of(
                "BankAccount", new ArrayList<>(),
                "Category", new ArrayList<>(),
                "Operation", new ArrayList<>()
        );
    }

    public boolean writeJson(PrintWriter printer) {
        var mapper = new ObjectMapper();
        boolean result = true;
        try {
            mapper.writeValue(printer, objects);
        } catch (IOException e) {
            result = false;
        }
        clear();
        return result;
    }
}
