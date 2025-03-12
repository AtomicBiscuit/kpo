import org.springframework.boot.SpringApplication;
import zoo.ZooApplication;

/**
 * Класс-приложение.
 */
public class App {
    /**
     * Точка входа в программу.
     *
     * @param args аргументы командой строки (игнорируются)
     */
    public static void main(String[] args) {
        var context = SpringApplication.run(ZooApplication.class, args);

        var app = context.getBean(ZooApplication.class);

        app.run();
    }
}
