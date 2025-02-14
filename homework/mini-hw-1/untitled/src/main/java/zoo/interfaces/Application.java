package zoo.interfaces;

import zoo.domains.Zoo;

/**
 * Интерфейс приложения-зоопарка.
 */
public interface Application {
    /**
     * Возвращает объект зоопарка.
     *
     * @return значение zoo
     */
    Zoo getZoo();

    /**
     * Заменяет активное меню на новое.
     *
     * @param newMenuName имя установленного меню
     */
    void changeMenu(String newMenuName);
}
