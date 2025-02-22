package zoo.domains.herbos;

import lombok.Getter;
import lombok.Setter;
import zoo.domains.Animal;
import zoo.interfaces.Friendly;

/**
 * Класс травоядных животных.
 */
@Setter
@Getter
public abstract class Herbo extends Animal implements Friendly {
    protected int kindness;

    public Herbo(int food, int number, String name, int kindness) {
        super(food, number, name);
        this.kindness = kindness;
    }
}
