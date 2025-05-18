package zoo.service.observers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.stereotype.Component;
import zoo.domain.reports.Report;
import zoo.domain.reports.ReportBuilder;
import zoo.domain.schedule.FeedingSchedule;

/**
 * Наблюдатель за кормлением животных.
 */
@Component
@RequiredArgsConstructor
public class FeedingTimeObserver implements Observer<FeedingSchedule> {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final ReportBuilder reportBuilder = new ReportBuilder();

    /**
     * Сохраняет информацию о наступлении времени для кормления согласно расписанию.
     *
     * @param schedule расписание
     */
    @Override
    @Synchronized
    public void onAction(FeedingSchedule schedule) {
        if (Objects.isNull(schedule)) {
            return;
        }
        String message = String.format(
                "по расписанию ID-%d для животного ID-%d в %s полагается %s",
                schedule.getId(),
                schedule.getAnimal().getId(),
                schedule.getSchedule().format(TIME_FORMATTER),
                schedule.getFoodName()
        );
        reportBuilder.addOperation("Настало время кормления", message);
    }

    @Override
    @Synchronized
    public Report buildReport() {
        return reportBuilder.build();
    }

    /**
     * Сохраняет событие о кормлении животного.
     */
    @Synchronized
    public void onFeed(Integer animalId, String food, LocalDateTime time) {
        String message = String.format(
                "В %s животное ID-%d съело %s",
                DATE_TIME_FORMATTER.format(time),
                animalId,
                food
        );
        reportBuilder.addOperation("Кормление", message);
    }
}