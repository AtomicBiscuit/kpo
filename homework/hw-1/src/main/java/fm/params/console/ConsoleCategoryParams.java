package fm.params.console;

import fm.enums.OperationType;
import fm.helpers.ConsoleReader;
import fm.params.CategoryParams;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * Класс для получения параметров создания категории из потока ввода.
 */
@Setter
@Component
public class ConsoleCategoryParams implements CategoryParams {
    ConsoleReader helper;

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
