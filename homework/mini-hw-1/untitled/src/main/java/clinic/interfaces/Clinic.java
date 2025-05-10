package clinic.interfaces;

import zoo.domains.Animal;

/**
 * Интерфейс для ветеринарной клиники.
 */
public interface Clinic {
    /**
     * Проверяет здоровье животного.
     *
     * @param animal животное для проверки
     * @return true, если зверь здоров
     */
    boolean checkAnimal(Animal animal);
}
