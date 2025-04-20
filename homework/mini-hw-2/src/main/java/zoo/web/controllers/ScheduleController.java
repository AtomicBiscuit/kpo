package zoo.web.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zoo.domain.animals.AnimalProvider;
import zoo.domain.exceptions.ZooException;
import zoo.domain.schedule.FeedingSchedule;
import zoo.domain.schedule.FeedingScheduleFactory;
import zoo.domain.schedule.FeedingScheduleProvider;
import zoo.web.dto.requests.ScheduleCreateRequest;
import zoo.web.dto.requests.ScheduleUpdateRequest;
import zoo.web.dto.responses.ScheduleResponse;

/**
 * Контроллер для обработки запросов, связанных с расписанием.
 */
@RestController
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
@Tag(name = "Расписание кормления", description = "Управление расписанием")
@Service
public class ScheduleController {
    private final AnimalProvider animalProvider;

    private final FeedingScheduleProvider feedingScheduleProvider;

    private final FeedingScheduleFactory feedingScheduleFactory;

    /**
     * Возвращает расписание по Id.
     *
     * @param id идентификатор
     * @return Представление в виде HttpResponse {@link FeedingSchedule}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponse> getById(@PathVariable int id) {
        var schedule = feedingScheduleProvider.getScheduleById(id);
        if (schedule == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ScheduleResponse.from(schedule));
    }

    /**
     * Возвращает полное расписание.
     *
     * @return Представление в виде HttpResponse {@link FeedingSchedule List of FeedingSchedule}
     */
    @GetMapping("/all")
    public ResponseEntity<List<ScheduleResponse>> getAll() {
        var schedules = feedingScheduleProvider.getSchedules();
        if (schedules == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(schedules.stream().map(ScheduleResponse::from).toList());
    }

    /**
     * Обработка запроса на добавления кормления в расписание.
     *
     * @param request {@link }
     * @return Представление в виде HttpResponse {@link FeedingSchedule}
     */
    @PostMapping("/create")
    @Operation(summary = "Запланировать кормление")
    public ResponseEntity<ScheduleResponse> create(@Valid @RequestBody ScheduleCreateRequest request,
                                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ZooException(bindingResult.getAllErrors().getFirst().getDefaultMessage());
        }

        var animal = animalProvider.getAnimalById(request.animalId());

        if (animal == null) {
            throw new ZooException("Can`t find animal with id: " + request.animalId());
        }

        var schedule = feedingScheduleProvider.save(feedingScheduleFactory.createSchedule(
                animal,
                request.dailyTime(),
                request.foodName()
        ));

        return ResponseEntity.status(HttpStatus.CREATED).body(ScheduleResponse.from(schedule));
    }

    /**
     * Обработка запроса на изменение расписания.
     *
     * @param request {@link ScheduleUpdateRequest}
     * @return Представление в виде HttpResponse {@link FeedingSchedule}
     */
    @PostMapping("/update")
    @Operation(summary = "Изменить расписание",
            description = "Оставляйте поля пустыми, чтобы сохранить старое значение")
    public ResponseEntity<ScheduleResponse> update(@Valid @RequestBody ScheduleUpdateRequest request,
                                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ZooException(bindingResult.getAllErrors().getFirst().getDefaultMessage());
        }

        var schedule = feedingScheduleProvider.getScheduleById(request.scheduleId());

        if (Objects.isNull(schedule)) {
            return ResponseEntity.notFound().build();
        }

        if (Objects.nonNull(request.dailyTime())) {
            schedule.setSchedule(request.dailyTime());
        }
        if (Objects.nonNull(request.foodName())) {
            schedule.setFoodName(request.foodName());
        }
        return ResponseEntity.ok(ScheduleResponse.from(feedingScheduleProvider.save(schedule)));
    }

    /**
     * Удаляет расписание по Id.
     *
     * @param id идентификатор
     * @return HttpStatus Ok
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить из расписания")
    public ResponseEntity<Void> removeById(@PathVariable int id) {
        feedingScheduleProvider.deleteById(id);
        return ResponseEntity.ok().build();
    }
}