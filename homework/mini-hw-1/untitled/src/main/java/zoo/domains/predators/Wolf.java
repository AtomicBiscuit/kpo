package zoo.domains.predators;

/**
 * Класс представляющий волка.
 */
public class Wolf extends Predator {
    public Wolf(int food, int number, String name) {
        super(food, number, name);
    }

    @Override
    public String toString() {
        return String.format("Wolf \033[31;1m%s\033[0m with food consumption %d, inventory number %d", name, food,
                             number);
    }
}
