package zoo.domains;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import zoo.interfaces.Alive;
import zoo.interfaces.Inventory;
import zoo.interfaces.Named;

/**
 * Класс для представления животного.
 */
@Setter
@Getter
@AllArgsConstructor
public abstract class Animal implements Alive, Inventory, Named {
    protected int food;

    protected int number;

    protected String name;
}
