package clinic;

import clinic.interfaces.Clinic;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import zoo.domains.Animal;

/**
 * Класс для имитации логики ветеринарной клиники.
 */
public class ZooClinic implements Clinic {
    @Autowired
    Random randomGenerator;

    /**
     * Имитирует проверку здоровья животного.
     *
     * @param animal животное для проверки
     * @return true, с вероятностью 0.5
     */
    @Override

    public boolean checkAnimal(Animal animal) {
        return randomGenerator.nextBoolean();
    }
}
