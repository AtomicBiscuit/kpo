package zoo.domains.predators;

/**
 * Класс представляющий тигра.
 */
public class Tiger extends Predator {
    public Tiger(int food, int number, String name) {
        super(food, number, name);
    }

    @Override
    public String toString() {
        return String.format("Tiger \033[31;1m%s\033[0m with food consumption %d, inventory number %d", name, food,
                             number);
    }
}
