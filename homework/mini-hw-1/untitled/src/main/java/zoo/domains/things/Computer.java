package zoo.domains.things;

import zoo.domains.Thing;

/**
 * Класс представляющий компьютер на балансе зоопарка.
 */
public class Computer extends Thing {
    public Computer(int number, String name) {
        super(number, name);
    }

    @Override
    public String toString() {
        return String.format("Computer \033[32;1m%s\033[0m with inventory number %d", name, number);
    }
}
