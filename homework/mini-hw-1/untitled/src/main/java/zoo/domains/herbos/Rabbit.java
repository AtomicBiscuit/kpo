package zoo.domains.herbos;

/**
 * Класс представляющий кролика.
 */
public class Rabbit extends Herbo {
    public Rabbit(int food, int number, int kindness, String name) {
        super(food, number, name, kindness);
    }

    @Override
    public String toString() {
        return String.format(
                "Rabbit \033[34;1m%s\033[0m with food consumption %d, inventory number %d and kindness %d",
                name, food, number, kindness
        );
    }
}
