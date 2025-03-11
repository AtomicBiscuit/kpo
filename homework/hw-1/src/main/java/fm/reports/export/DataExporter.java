package fm.reports.export;

import java.io.PrintWriter;
import org.springframework.stereotype.Component;

/**
 * Абстрактный класс для экспорта хранимых данных в разных форматах.
 */
@Component
public abstract class DataExporter {
    /**
     * Экспортирует данные в {@link PrintWriter printer} в порядке: счета, категории, операции.
     *
     * @param printer {@link PrintWriter} для записи данных
     */
    public void export(PrintWriter printer) {
        exportAccounts(printer);
        exportCategories(printer);
        exportOperations(printer);
        exportFinalize(printer);
    }

    protected abstract void exportAccounts(PrintWriter printer);

    protected abstract void exportCategories(PrintWriter printer);

    protected abstract void exportOperations(PrintWriter printer);

    protected abstract void exportFinalize(PrintWriter printer);
}
