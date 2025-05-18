package zoo.domain.schedule;

import java.time.LocalTime;
import java.util.List;

/**
 * Интерфейс для поставщиков.
 */
public interface FeedingScheduleProvider {
    public List<FeedingSchedule> getSchedules();

    public List<FeedingSchedule> getAnimalSchedules(Integer animalId);

    public List<FeedingSchedule> getSchedulesInInterval(LocalTime start, LocalTime end);

    public FeedingSchedule getScheduleById(Integer id);

    public FeedingSchedule save(FeedingSchedule schedule);

    public void deleteById(Integer scheduleId);
}
