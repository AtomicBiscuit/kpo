package zoo.domains.things;

import zoo.domains.Thing;

/**
 * Класс представляющий стол на балансе зоопарка.
 */
public class Table extends Thing {
    public Table(int number, String name) {
        super(number, name);
    }

    @Override
    public String toString() {
        return String.format("Table \033[36;1m%s\033[0m with inventory number %d", name, number);
    }
}
