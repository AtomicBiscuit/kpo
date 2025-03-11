package fm.helpers;

import java.io.InputStream;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ConsoleHelper {
    final Scanner scanner;

    final PrintStream output;

    SimpleDateFormat format;

    @Autowired
    public ConsoleHelper(InputStream source, PrintStream output, SimpleDateFormat format) {
        this.scanner = new Scanner(source);
        this.output = output;
        this.format = format;
    }

    /**
     * Считывает целое число из консоли.
     *
     * @return полученное число
     */
    public int readInt(String onInput, int min, int max) {
        do {
            output.print(onInput + ": ");
            String line = scanner.nextLine();
            try {
                int number = Integer.parseInt(line);
                if (min > number || number > max) {
                    output.printf("Number must be in range [%d, %d], try again%n", min, max);
                    continue;
                }
                return number;
            } catch (NumberFormatException exception) {
                output.println("Invalid number, try again.");
            }
        } while (true);
    }

    /**
     * Считывает строку из консоли.
     *
     * @return полученная строка
     */
    public String readLine(String onInput) {
        output.print(onInput + ": ");
        return scanner.nextLine();
    }

    /**
     * Считывает дату из консоли.
     *
     * @return полученная дата
     */
    public Date readDate(String onInput) {
        do {
            output.print(onInput + ": ");
            String line = scanner.nextLine();
            try {
                return format.parse(line);
            } catch (ParseException exception) {
                output.println("Invalid date, try again.");
            }
        } while (true);
    }
}
