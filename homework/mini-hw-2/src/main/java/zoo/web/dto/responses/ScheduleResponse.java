package zoo.web.dto.responses;

import java.time.LocalTime;
import lombok.Builder;
import zoo.domain.schedule.FeedingSchedule;

/**
 * DTO для записи расписания питания.
 */
@Builder
public record ScheduleResponse(
        Integer id,

        Integer animalId,

        LocalTime dailyTime,

        String foodName
) {
    /**
     * Создаёт DTO из объекта.
     *
     * @param schedule расписание для сериализации.
     * @return {@link ScheduleResponse}
     */
    public static ScheduleResponse from(FeedingSchedule schedule) {
        var builder = ScheduleResponse.builder();
        builder.id(schedule.getId());
        builder.animalId(schedule.getAnimal().getId());
        builder.dailyTime(schedule.getSchedule());
        builder.foodName(schedule.getFoodName());
        return builder.build();
    }
}
