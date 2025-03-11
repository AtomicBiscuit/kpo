package fm.reports.export.impl;

import fm.reports.export.DataExporter;
import fm.storages.AccountStorage;
import fm.storages.CategoryStorage;
import fm.storages.OperationStorage;
import fm.visitors.impl.CSVVisitor;
import java.io.PrintWriter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CSVDataExporter extends DataExporter {
    AccountStorage accountStorage;

    CategoryStorage categoryStorage;

    OperationStorage operationStorage;

    CSVVisitor visitor;

    @Override
    protected void exportAccounts(PrintWriter printer) {
        visitor.setWriter(printer);
        accountStorage.accept(visitor);
    }

    @Override
    protected void exportCategories(PrintWriter printer) {
        visitor.setWriter(printer);
        categoryStorage.accept(visitor);
    }

    @Override
    protected void exportOperations(PrintWriter printer) {
        visitor.setWriter(printer);
        operationStorage.accept(visitor);
    }

    @Override
    protected void exportFinalize(PrintWriter printer) {
        printer.flush();
    }
}
