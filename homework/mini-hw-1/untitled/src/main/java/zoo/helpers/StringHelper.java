package zoo.helpers;

import java.util.Scanner;
import org.springframework.stereotype.Component;

/**
 * Класс для ввода строк из консоли.
 */
@Component
public class StringHelper {
    Scanner scanner = new Scanner(System.in);

    /**
     * Считывает строку из консоли.
     *
     * @return полученная строка
     */
    public String read(String onInput) {
        System.out.print(onInput + ": ");
        return scanner.nextLine();
    }
}
