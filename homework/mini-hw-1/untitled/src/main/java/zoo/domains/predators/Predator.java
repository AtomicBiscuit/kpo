package zoo.domains.predators;

import zoo.domains.Animal;

/**
 * Класс хищных животных.
 */
public abstract class Predator extends Animal {
    public Predator(int food, int number, String name) {
        super(food, number, name);
    }
}