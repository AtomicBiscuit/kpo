package zoo.interfaces;

import zoo.ZooApplication;

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
