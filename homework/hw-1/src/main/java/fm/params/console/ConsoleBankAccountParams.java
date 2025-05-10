package fm.params.console;

import fm.helpers.ConsoleHelper;
import fm.params.BankAccountParams;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * Класс для получения параметров создания счёта из потока ввода.
 */
@Setter
@Component
@AllArgsConstructor
public class ConsoleBankAccountParams implements BankAccountParams {
    ConsoleHelper helper;

    public String getName() {
        return helper.readLine("Enter an account name");
    }

    public int getBalance() {
        return helper.readInt("Enter an account balance", -100_000_000, 100_000_000);
    }
}
