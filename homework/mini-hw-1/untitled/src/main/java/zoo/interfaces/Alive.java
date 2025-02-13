package zoo.interfaces;

import lombok.Getter;
import lombok.Setter;

/**
 * Интерфейс для живых существ.
 */
public interface Alive {
    /**
     * Устанавливает ежедневное потребление еды значением food.
     *
     * @param food новое значение дружелюбности
     */
    public void setFood(int food);

    /**
     * Возвращает ежедневное потребление еды.
     *
     * @return значение food
     */
    public int getFood();
}
