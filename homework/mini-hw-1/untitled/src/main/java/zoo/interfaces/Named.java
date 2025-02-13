package zoo.interfaces;

/**
 * Интерфейс именованных сущностей.
 */
public interface Named {
    /**
     * Устанавливает имя name.
     *
     * @param name новое значение имени
     */
    public void setName(String name);

    /**
     * Возвращает имя.
     *
     * @return значение name
     */
    public String getName();
}
