package fm.params.console;

import fm.enums.OperationType;
import fm.helpers.ConsoleReader;
import java.io.InputStream;
import java.io.PrintStream;

/**
 * Класс для получения параметров создания категории из потока ввода.
 */
public class ConsoleCategoryParams {
    ConsoleReader helper;

    public ConsoleCategoryParams(InputStream input, PrintStream output) {
        this.helper = new ConsoleReader(input, output);
    }

    public String getName() {
        return helper.readLine("Enter an category name");
    }

    public OperationType getType() {
        if (1 == helper.readInt("Enter an category type -  1 for incomes, 2 for expenses", 1, 2)) {
            return OperationType.INCOME;
        }
        return OperationType.EXPENSES;
    }
}
