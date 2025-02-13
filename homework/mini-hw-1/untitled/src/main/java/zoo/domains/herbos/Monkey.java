package zoo.domains.herbos;

/**
 * Класс представляющий обезьяну.
 */
public class Monkey extends Herbo {
    public Monkey(int food, int number, int kindness, String name) {
        super(food, number, name, kindness);
    }

    @Override
    public String toString() {
        return String.format(
                "Monkey \033[34;1m%s\033[0m with food consumption %d, inventory number %d and kindness %d",
                name, food, number, kindness
        );
    }
}
