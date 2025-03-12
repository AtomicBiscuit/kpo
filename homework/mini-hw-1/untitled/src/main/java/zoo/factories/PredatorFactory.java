package zoo.factories;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.util.Tuple;
import zoo.domains.predators.Predator;
import zoo.domains.predators.Tiger;
import zoo.domains.predators.Wolf;
import zoo.helpers.IntHelper;
import zoo.helpers.StringHelper;

/**
 * Фабрика для создания травоядных животных.
 * При создании считывает параметры из консоли.
 */
@Component
public class PredatorFactory {
    @Autowired
    IntHelper intHelper;

    @Autowired
    StringHelper stringHelper;

    public static List<String> predatorsList = List.of("Wolf", "Tiger");

    /**
     * Считывает из консоли суточное потребление пищи и инвентаризационный номер.
     *
     * @return Список из считанных параметров
     */
    private Tuple<List<Integer>, String> readPredatorParams() {
        int food = intHelper.read("Enter food consumption per day", 0, 1_000_000_000);
        int num = intHelper.read("Enter inventory number", 0, 1_000_000_000);
        String name = stringHelper.read("Enter animal name");

        return new Tuple<>(List.of(food, num), name);
    }

    /**
     * Создаёт новое хищное животное по названию с параметрами, считанными с клавиатуры.
     *
     * @return новое животное
     */
    public Predator create(String name) {
        return switch (name) {
            case "wolf" -> createWolf();
            case "tiger" -> createTiger();
            default -> null;
        };
    }

    /**
     * Создаёт нового волка с параметрами, считанными с клавиатуры.
     *
     * @return новый объект волка
     */
    public Wolf createWolf() {
        var params = readPredatorParams();
        return new Wolf(params._1().get(0), params._1().get(1), params._2());
    }

    /**
     * Создаёт нового тигра с параметрами, считанными с клавиатуры.
     *
     * @return новый объект тигра
     */
    public Tiger createTiger() {
        var params = readPredatorParams();
        return new Tiger(params._1().get(0), params._1().get(1), params._2());
    }
}
