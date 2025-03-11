package fm.params.console;

import fm.helpers.ConsoleReader;
import java.io.InputStream;
import java.io.PrintStream;

/**
 * Класс для получения параметров создания счёта из потока ввода.
 */
public class ConsoleBankAccountParams {
    ConsoleReader helper;

    public ConsoleBankAccountParams(InputStream input, PrintStream output) {
        this.helper = new ConsoleReader(input, output);
    }

    public String getName() {
        return helper.readLine("Enter an account name");
    }

    public int getBalance() {
        return helper.readInt("Enter an account balance", -100_000_000, 100_000_000);
    }
}
