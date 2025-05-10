package fm.params.console;

import fm.enums.OperationType;
import fm.helpers.ConsoleHelper;
import fm.params.CategoryParams;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * Класс для получения параметров создания категории из потока ввода.
 */
@Setter
@Component
@AllArgsConstructor
public class ConsoleCategoryParams implements CategoryParams {
    ConsoleHelper helper;

    public String getName() {
        return helper.readLine("Enter a category name");
    }

    public OperationType getType() {
        if (1 == helper.readInt("Enter a category type -  1 for incomes, 2 for expenses", 1, 2)) {
            return OperationType.INCOME;
        }
        return OperationType.EXPENSES;
    }
}
