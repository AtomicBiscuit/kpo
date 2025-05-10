package zoo.interfaces;

/**
 * Интерфейс для живых существ.
 */
public interface Alive {
    /**
     * Устанавливает ежедневное потребление еды значением food.
     *
     * @param food новое значение дружелюбности
     */
    void setFood(int food);

    /**
     * Возвращает ежедневное потребление еды.
     *
     * @return значение food
     */
    int getFood();
}
