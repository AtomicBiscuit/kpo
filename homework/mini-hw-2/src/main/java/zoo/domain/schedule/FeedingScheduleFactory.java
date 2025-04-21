package zoo.domain.schedule;

import java.time.LocalTime;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import zoo.domain.animals.Animal;

/**
 * Фабрика для порождения вольеров.
 */
@Component
@NoArgsConstructor
public class FeedingScheduleFactory {
    public FeedingSchedule createSchedule(Animal animal, LocalTime schedule, String foodName) {
        return new FeedingSchedule(animal, schedule, foodName);
    }
}
