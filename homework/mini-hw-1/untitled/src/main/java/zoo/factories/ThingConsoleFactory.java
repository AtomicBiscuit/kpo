package zoo.factories;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.util.Tuple;
import zoo.domains.Thing;
import zoo.domains.predators.Predator;
import zoo.domains.things.Computer;
import zoo.domains.things.Table;
import zoo.helpers.IntHelper;
import zoo.helpers.StringHelper;

/**
 * Фабрика для создания вещей.
 * При создании считывает параметры из консоли.
 */
@Component
public class ThingConsoleFactory {

    @Autowired
    IntHelper intHelper;

    @Autowired
    StringHelper stringHelper;

    public static List<String> predatorsList = List.of("Computer", "Table");

    /**
     * Считывает из консоли инвентаризационный номер и название.
     *
     * @return Список из считанных параметров
     */
    private Tuple<Integer, String> readThingParams() {
        int num = intHelper.read("Enter inventory number", 0, 1_000_000_000);
        String name = stringHelper.read("Enter name");

        return new Tuple<>(num, name);
    }

    /**
     * Создаёт новую вещь по названию с параметрами, считанными с клавиатуры.
     *
     * @return новая вещь
     */
    public Thing create(String name) {
        return switch (name) {
            case "computer" -> createComputer();
            case "table" -> createTable();
            default -> null;
        };
    }


    /**
     * Создаёт новый компьютер с параметрами, считанными с клавиатуры.
     *
     * @return новый объект компьютера
     */
    public Computer createComputer() {
        var params = readThingParams();
        return new Computer(params._1(), params._2());
    }

    /**
     * Создаёт новый стол с параметрами, считанными с клавиатуры.
     *
     * @return новый объект стола
     */
    public Table createTable() {
        var params = readThingParams();
        return new Table(params._1(), params._2());
    }
}
