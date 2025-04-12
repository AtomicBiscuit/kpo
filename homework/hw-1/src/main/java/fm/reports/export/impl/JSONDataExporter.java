package fm.reports.export.impl;

import fm.reports.export.DataExporter;
import fm.storages.AccountStorage;
import fm.storages.CategoryStorage;
import fm.storages.OperationStorage;
import fm.visitors.impl.JSONVisitor;
import java.io.PrintWriter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class JSONDataExporter extends DataExporter {
    AccountStorage accountStorage;

    CategoryStorage categoryStorage;

    OperationStorage operationStorage;

    JSONVisitor visitor;

    @Override
    protected void exportAccounts(PrintWriter printer) {
        accountStorage.accept(visitor);
    }

    @Override
    protected void exportCategories(PrintWriter printer) {
        categoryStorage.accept(visitor);
    }

    @Override
    protected void exportOperations(PrintWriter printer) {
        operationStorage.accept(visitor);
    }

    @Override
    protected void exportFinalize(PrintWriter printer) {
        if (!visitor.writeJson(printer)) {
            log.error("Error occurred while trying to write json.");
        }
    }
}
