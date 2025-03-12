package fm.visitors.impl;

import fm.domains.BankAccount;
import fm.domains.Category;
import fm.domains.Operation;
import fm.visitors.Visitor;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * Посетитель для записи данных в CSV формате.
 */
@Setter
@Component
@NoArgsConstructor
public class CSVVisitor implements Visitor {
    PrintWriter writer;

    static final String SEPARATOR = ";";

    @Override
    public void visit(BankAccount account) {
        writer.println(String.join(
                SEPARATOR,
                account.getId().toString(),
                account.getName(),
                String.valueOf(account.getBalance())
        ));
    }

    @Override
    public void visit(Category category) {
        writer.println(String.join(
                SEPARATOR,
                category.getId().toString(),
                category.getName(),
                category.getType().toString()
        ));
    }

    @Override
    public void visit(Operation operation) {
        var dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        writer.println(String.join(
                SEPARATOR,
                operation.getId().toString(),
                operation.getBankAccountId().toString(),
                operation.getCategoryId().toString(),
                operation.getType().toString(),
                String.valueOf(operation.getAmount()),
                operation.getName(),
                operation.getDescription(),
                dateFormat.format(operation.getDate())
        ));
    }
}
