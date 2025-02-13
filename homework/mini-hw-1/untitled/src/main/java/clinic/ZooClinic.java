package clinic;

import clinic.interfaces.Clinic;
import java.util.Random;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import zoo.domains.Animal;

/**
 * Класс для имитации логики ветеринарной клиники.
 */
@Component
@NoArgsConstructor
public class ZooClinic implements Clinic {
    Random randomGenerator = new Random();

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
