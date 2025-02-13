package zoo.helpers;

import java.util.InputMismatchException;
import java.util.Scanner;
import org.springframework.stereotype.Component;

/**
 * Класс для ввода чисел из консоли.
 */
@Component
public class IntHelper {
    Scanner scanner = new Scanner(System.in);

    /**
     * Считывает целое число из консоли.
     *
     * @return полученное число
     */
    public int read(String onInput, int min, int max) {
        do {
            System.out.print(onInput + ": ");
            String line = scanner.nextLine();
            try {
                int number = Integer.parseInt(line);
                if (min > number || number > max) {
                    System.out.printf("Number must be in range [%d, %d], try again\n", min, max);
                    continue;
                }
                return number;
            } catch (NumberFormatException exception) {
                System.out.println("Invalid number, try again.");
            }
        } while (true);
    }
}
