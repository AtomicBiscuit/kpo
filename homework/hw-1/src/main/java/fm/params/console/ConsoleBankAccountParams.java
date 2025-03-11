package fm.params.console;

import fm.helpers.ConsoleReader;
import fm.params.BankAccountParams;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * Класс для получения параметров создания счёта из потока ввода.
 */
@Setter
@Component
public class ConsoleBankAccountParams implements BankAccountParams {
    ConsoleReader helper;

    public String getName() {
        return helper.readLine("Enter an account name");
    }

    public int getBalance() {
        return helper.readInt("Enter an account balance", -100_000_000, 100_000_000);
    }
}
