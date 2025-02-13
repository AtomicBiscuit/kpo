package zoo.interfaces;

/**
 * Интерфейс сущностей, подлежащих инвентаризации.
 */
public interface Inventory {
    /**
     * Устанавливает инвентаризационный номер number.
     *
     * @param number новое значение дружелюбности
     */
    void setNumber(int number);

    /**
     * Возвращает инвентаризационный номер.
     *
     * @return значение number
     */
    int getNumber();
}
