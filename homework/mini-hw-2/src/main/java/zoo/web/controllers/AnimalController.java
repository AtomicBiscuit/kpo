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
import zoo.domain.animals.Animal;
import zoo.domain.animals.AnimalFactory;
import zoo.domain.animals.AnimalProvider;
import zoo.domain.animals.AnimalSex;
import zoo.domain.exceptions.ZooException;
import zoo.domain.schedule.FeedingSchedule;
import zoo.domain.schedule.FeedingScheduleProvider;
import zoo.service.AnimalTransferService;
import zoo.service.FeedingOrganizationService;
import zoo.web.dto.requests.AnimalCreateRequest;
import zoo.web.dto.requests.AnimalFeedRequest;
import zoo.web.dto.requests.AnimalMoveRequest;
import zoo.web.dto.requests.AnimalUpdateRequest;
import zoo.web.dto.responses.AnimalResponse;
import zoo.web.dto.responses.ScheduleResponse;

/**
 * Контроллер для обработки запросов, связанных с животными.
 */
@RestController
@RequestMapping("/api/animals")
@RequiredArgsConstructor
@Tag(name = "Животные", description = "Управление животными")
@Service
public class AnimalController {
    private final AnimalProvider animalProvider;

    private final FeedingScheduleProvider feedingScheduleProvider;

    private final AnimalFactory animalFactory;

    private final AnimalTransferService animalTransferService;

    private final FeedingOrganizationService feedingOrganizationService;

    /**
     * Возвращает животное по Id.
     *
     * @param id идентификатор
     * @return Представление в виде HttpResponse {@link Animal}
     */
    @GetMapping("/{id}")
    public ResponseEntity<AnimalResponse> getById(@PathVariable int id) {
        var animal = animalProvider.getAnimalById(id);
        if (animal == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(AnimalResponse.from(animal));
    }

    /**
     * Возвращает список из всех животных.
     *
     * @return Представление в виде HttpResponse {@link Animal List of Animal}
     */
    @GetMapping("/all")
    public ResponseEntity<List<AnimalResponse>> getAll() {
        var animals = animalProvider.getAnimals();
        if (animals == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(animals.stream().map(AnimalResponse::from).toList());
    }

    /**
     * Возвращает полное расписание кормления животного.
     *
     * @return Представление в виде HttpResponse {@link FeedingSchedule List of FeedingSchedule}
     */
    @GetMapping("/{id}/schedule")
    public ResponseEntity<List<ScheduleResponse>> getAllSchedulesAttachedToAnimal(@PathVariable int id) {
        var animal = animalProvider.getAnimalById(id);
        if (animal == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(feedingScheduleProvider.getAnimalSchedules(id)
                                                        .stream()
                                                        .map(ScheduleResponse::from)
                                                        .toList());
    }

    /**
     * Обрабатывает запрос на кормление животного.
     */
    @PostMapping("/feed")
    public ResponseEntity<Void> feed(@Valid @RequestBody AnimalFeedRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ZooException(bindingResult.getAllErrors().getFirst().getDefaultMessage());
        }

        feedingOrganizationService.feedAnimalById(request.id(), request.foodName());

        return ResponseEntity.ok().build();
    }

    /**
     * Обработка запроса на создание животного.
     *
     * @param request {@link AnimalCreateRequest}
     * @return Представление в виде HttpResponse {@link Animal}
     */
    @PostMapping("/create")
    @Operation(summary = "Добавить животное")
    public ResponseEntity<AnimalResponse> create(@Valid @RequestBody AnimalCreateRequest request,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ZooException(bindingResult.getAllErrors().getFirst().getDefaultMessage());
        }

        var sex = AnimalSex.fromString(request.sex());
        if (sex.isEmpty()) {
            throw new ZooException("Unknown animal sex: " + request.sex());
        }

        var animal = animalProvider.save(animalFactory.createAnimal(
                request.type(),
                request.name(),
                request.birthday(),
                sex.get(),
                request.favoriteFood(),
                request.isHealthy()
        ));

        return ResponseEntity.status(HttpStatus.CREATED).body(AnimalResponse.from(animal));
    }

    /**
     * Обработка запроса на перемещение животного.
     *
     * @param request {@link AnimalMoveRequest}
     * @return Представление в виде HttpResponse {@link Animal}
     */
    @PostMapping("/move")
    @Operation(summary = "Переместить животное")
    public ResponseEntity<AnimalResponse> move(@Valid @RequestBody AnimalMoveRequest request,
                                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ZooException(bindingResult.getAllErrors().getFirst().getDefaultMessage());
        }

        var animal = animalTransferService.moveAnimalById(request.animalId(), request.enclosureId());
        return ResponseEntity.status(HttpStatus.CREATED).body(AnimalResponse.from(animal));
    }

    /**
     * Обработка запроса на изменение животного.
     *
     * @param request {@link AnimalUpdateRequest}
     * @return Представление в виде HttpResponse {@link Animal}
     */
    @PostMapping("/update")
    @Operation(summary = "Изменить данные животного", description = "Оставляйте поля пустыми, чтобы сохранить старое "
                                                                            + "значение")
    public ResponseEntity<AnimalResponse> update(@Valid @RequestBody AnimalUpdateRequest request,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ZooException(bindingResult.getAllErrors().getFirst().getDefaultMessage());
        }

        var animal = animalProvider.getAnimalById(request.id());

        if (Objects.isNull(animal)) {
            return ResponseEntity.notFound().build();
        }

        if (Objects.nonNull(request.name())) {
            animal.setName(request.name());
        }
        if (Objects.nonNull(request.type())) {
            animal.setType(request.type());
        }
        if (Objects.nonNull(request.sex())) {
            var sex = AnimalSex.fromString(request.sex());
            if (sex.isEmpty()) {
                throw new ZooException("Unknown animal sex: " + request.sex());
            }
            animal.setSex(sex.get());
        }
        if (Objects.nonNull(request.birthday())) {
            animal.setBirthday(request.birthday());
        }
        if (Objects.nonNull(request.favoriteFood())) {
            animal.setFavoriteFood(request.favoriteFood());
        }
        if (Objects.nonNull(request.isHealthy())) {
            animal.setHealthy(request.isHealthy());
        }
        return ResponseEntity.ok(AnimalResponse.from(animalProvider.save(animal)));
    }

    /**
     * Удаляет животное по Id.
     *
     * @param id идентификатор
     * @return HttpStatus Ok
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить животное")
    public ResponseEntity<Void> removeById(@PathVariable int id) {
        animalProvider.deleteById(id);
        return ResponseEntity.ok().build();
    }
}