package zoo.repository;

import java.time.LocalTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import zoo.domain.schedule.FeedingSchedule;
import zoo.domain.schedule.FeedingScheduleProvider;

/**
 * Unit-of-work для работы с таблицей животных.
 */
public interface FeedingScheduleRepository extends JpaRepository<FeedingSchedule, Integer>, FeedingScheduleProvider {
    @Query("""
            SELECT fs FROM FeedingSchedule fs
            """)
    @Override
    List<FeedingSchedule> getSchedules();

    @Query("""
            SELECT fs FROM FeedingSchedule fs
                     WHERE fs.animal.id = :animalId
            """)
    @Override
    List<FeedingSchedule> getAnimalSchedules(Integer animalId);

    @Query("""
            SELECT fs FROM FeedingSchedule fs
                     WHERE fs.schedule BETWEEN :start AND :end
            """)
    @Override
    List<FeedingSchedule> getSchedulesInInterval(LocalTime start, LocalTime end);

    @Query("""
            SELECT fs FROM FeedingSchedule fs
                     WHERE fs.id = :id
            """)
    @Override
    FeedingSchedule getScheduleById(Integer id);
}
