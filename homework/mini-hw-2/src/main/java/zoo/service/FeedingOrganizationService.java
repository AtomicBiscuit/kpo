package zoo.service;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import zoo.domain.animals.AnimalProvider;
import zoo.domain.exceptions.ZooException;
import zoo.domain.reports.Report;
import zoo.domain.schedule.FeedingSchedule;
import zoo.domain.schedule.FeedingScheduleProvider;
import zoo.service.observers.FeedingTimeObserver;

/**
 * Сервис для организации процесса кормления.
 */
@Service
@RequiredArgsConstructor
public class FeedingOrganizationService {
    private final FeedingTimeObserver observer;

    private final FeedingScheduleProvider feedingProvider;

    private final AnimalProvider animalProvider;

    private LocalTime last = LocalTime.now();

    /**
     * Периодически проверяет, каких животных пора кормить в соответствии с расписанием.
     */
    @Scheduled(fixedDelay = 3 * 1000, initialDelay = 10 * 1000)
    public void checkSchedule() {
        var timestamp = LocalTime.now();

        List<FeedingSchedule> occurred;

        if (last.isAfter(timestamp)) {
            occurred = feedingProvider.getSchedulesInInterval(last, LocalTime.MAX);
            occurred.addAll(feedingProvider.getSchedulesInInterval(LocalTime.MIN, timestamp));
        } else {
            occurred = feedingProvider.getSchedulesInInterval(last, timestamp);
        }

        last = timestamp;

        if (Objects.nonNull(occurred)) {
            occurred.forEach(observer::onAction);
        }
    }

    /**
     * Создаёт событие о кормлении животного.
     */
    public void feedAnimalById(@NotNull Integer animalId, String food) {
        if (animalProvider.getAnimalById(animalId) == null) {
            throw new ZooException("Animal with id " + animalId + " not found");
        }
        observer.onFeed(animalId, food, LocalDateTime.now());
    }

    /**
     * Генерирует отчёт о кормлении животных.
     *
     * @return {@link Report}
     */
    public Report getReport() {
        return observer.buildReport();
    }
}
