package zoo.interfaces;

/**
 * Интерфейс для дружелюбных сущностей.
 */
public interface Friendly {
    /**
     * Устанавливает уровень дружелюбности равный kindness.
     *
     * @param kindness новое значение дружелюбности
     */
    void setKindness(int kindness);

    /**
     * Возвращает уровень доброты.
     *
     * @return значение kindness
     */
    int getKindness();
}
