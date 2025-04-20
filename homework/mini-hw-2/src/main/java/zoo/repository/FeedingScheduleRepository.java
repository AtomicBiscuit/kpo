package zoo.repository;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import org.hibernate.annotations.processing.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import zoo.domain.schedule.FeedingSchedule;
import zoo.domain.schedule.FeedingScheduleProvider;

/**
 * Unit-of-work для работы с таблицей животных.
 */
public interface FeedingScheduleRepository extends JpaRepository<FeedingSchedule, Integer>, FeedingScheduleProvider {
    @SQL("""
            SELECT * FROM feedingschedule
            """)
    @Override
    default List<FeedingSchedule> getSchedules() {
        return Collections.emptyList();
    }

    @SQL("""
            SELECT * FROM feedingschedule
                     WHERE animal_id = :animalId
            """)
    @Override
    default List<FeedingSchedule> getAnimalSchedules(Integer animalId) {
        return Collections.emptyList();
    }

    @SQL("""
            SELECT * FROM feedingSchedule
                     WHERE schedule BETWEEN :start AND :end
            """)
    @Override
    default List<FeedingSchedule> getSchedulesInInterval(LocalTime start, LocalTime end) {
        return Collections.emptyList();
    }

    @SQL("""
            SELECT * FROM feedingschedule
                     WHERE id = :id
            """)
    @Override
    default FeedingSchedule getScheduleById(Integer id) {
        return null;
    }
}
