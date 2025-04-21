package zoo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import zoo.domain.animals.Animal;
import zoo.domain.animals.AnimalProvider;
import zoo.domain.enclosures.EnclosureProvider;
import zoo.domain.schedule.FeedingSchedule;
import zoo.domain.schedule.FeedingScheduleProvider;

/**
 * Сервис для перевода животных между сервисами.
 */
@Service
@AllArgsConstructor
public class ZooStatisticService {
    private final AnimalProvider animalProvider;

    private final EnclosureProvider enclosureProvider;

    private final FeedingScheduleProvider feedingScheduleProvider;

    /**
     * Высчитывает число животных с заданной любимой едой.
     */
    public Integer countAnimalsWithFavoriteFood(String favoriteFood) {
        favoriteFood = favoriteFood.toLowerCase();
        return Math.toIntExact(animalProvider.getAnimals()
                                             .stream()
                                             .map(Animal::getFavoriteFood)
                                             .map(String::toLowerCase)
                                             .filter(favoriteFood::equals)
                                             .count());
    }

    /**
     * Высчитывает число животных, которые по расписанию получают определенную пищу.
     */
    public Integer countAnimalsFedWithFood(String food) {
        final String foodLower = food.toLowerCase();
        var schedules = feedingScheduleProvider.getSchedules();
        return Math.toIntExact(schedules.stream()
                                        .filter(schedule -> schedule.getFoodName().toLowerCase().equals(foodLower))
                                        .map(FeedingSchedule::getAnimal)
                                        .mapToInt(Animal::getId)
                                        .distinct()
                                        .count());
    }
}
