package fm.params.console;

import fm.domains.types.Identifier;
import fm.enums.OperationType;
import fm.helpers.ConsoleReader;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Date;

/**
 * Класс для получения параметров операции из потока ввода.
 */
public class ConsoleOperationParams {
    ConsoleReader helper;

    public ConsoleOperationParams(InputStream input, PrintStream output) {
        this.helper = new ConsoleReader(input, output);
    }

    public Identifier getBankAccountId() {
        return new Identifier(helper.readInt("Enter a target bank account id", 0, 1_000_000_000));
    }

    public Identifier getCategoryId() {
        return new Identifier(helper.readInt("Enter a target category id", 0, 1_000_000_000));
    }

    public OperationType getType() {
        if (1 == helper.readInt("Enter a category type -  1 for incomes, 2 for expenses", 1, 2)) {
            return OperationType.INCOME;
        }
        return OperationType.EXPENSES;
    }

    public int getAmount() {
        return helper.readInt("Enter a operation value", 0, 1_000_000_000);
    }

    public String getName() {
        return helper.readLine("Enter an operation name");
    }

    public String getDescription() {
        return helper.readLine("Enter an operation description");
    }

    public Date getDate() {
        return helper.readDate("Enter an operation date");
    }
}
