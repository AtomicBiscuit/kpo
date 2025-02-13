package zoo.domains;

import lombok.Getter;
import lombok.Setter;
import zoo.interfaces.Inventory;
import zoo.interfaces.Named;

/**
 * Класс представляющий вещи.
 */
@Getter
@Setter
public class Thing implements Inventory, Named {
    protected int number;

    protected String name;

    public Thing(int number, String name) {
        this.number = number;
        this.name = name;
    }
}
