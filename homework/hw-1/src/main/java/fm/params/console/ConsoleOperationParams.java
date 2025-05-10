package fm.params.console;

import fm.domains.types.Identifier;
import fm.helpers.ConsoleHelper;
import fm.params.OperationParams;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * Класс для получения параметров операции из потока ввода.
 */
@Setter
@Component
@AllArgsConstructor
public class ConsoleOperationParams implements OperationParams {
    ConsoleHelper helper;

    public Identifier getBankAccountId() {
        return new Identifier(helper.readInt("Enter a target bank account id", Identifier.MIN_ID, Identifier.MAX_ID));
    }

    public Identifier getCategoryId() {
        return new Identifier(helper.readInt("Enter a target category id", Identifier.MIN_ID, Identifier.MAX_ID));
    }

    public int getAmount() {
        return helper.readInt("Enter an operation value", 0, 1_000_000_000);
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
