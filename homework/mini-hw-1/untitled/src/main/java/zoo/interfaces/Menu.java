package zoo.interfaces;

/**
 * Интерфейс консольного меню.
 */
public interface Menu {
    /**
     * Выводит меню в консоль.
     */
    void print();

    /**
     * Получает и обрабатывает ответ пользователя.
     */
    void doLogic();
}
