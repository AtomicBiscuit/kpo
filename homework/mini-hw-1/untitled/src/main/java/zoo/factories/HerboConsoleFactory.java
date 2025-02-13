package zoo.factories;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.util.Tuple;
import zoo.domains.herbos.Monkey;
import zoo.domains.herbos.Rabbit;
import zoo.helpers.IntHelper;
import zoo.helpers.StringHelper;

/**
 * Фабрика для создания травоядных животных.
 * При создании считывает параметры из консоли.
 */
@Component
public class HerboConsoleFactory {
    @Autowired
    IntHelper intHelper;

    @Autowired
    StringHelper stringHelper;

    /**
     * Считывает из консоли суточное потребление пищи, инвентаризационный номер и доброту.
     *
     * @return Список из считанных параметров
     */
    private Tuple<List<Integer>, String> readHerboParams() {
        int food = intHelper.read("Enter food consumption per day", 0, 1_000_000_000);
        int num = intHelper.read("Enter inventory number", 0, 1_000_000_000);
        int kindness = intHelper.read("Enter kindness level", 0, 10);
        String name = stringHelper.read("Enter animal name");

        return new Tuple<>(List.of(food, num, kindness), name);
    }

    /**
     * Создаёт новую обезьяну с параметрами, считанными с клавиатуры.
     *
     * @return новый объект обезьяны
     */
    public Monkey createMonkey() {
        var params = readHerboParams();
        return new Monkey(params._1().get(0), params._1().get(1), params._1().get(2), params._2());
    }

    /**
     * Создаёт нового кролика с параметрами, считанными с клавиатуры.
     *
     * @return новый объект кролика
     */
    public Rabbit createRabbit() {
        var params = readHerboParams();
        return new Rabbit(params._1().get(0), params._1().get(1), params._1().get(2), params._2());
    }
}
