package zoo.web.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zoo.domain.exceptions.ZooException;
import zoo.domain.reports.Report;
import zoo.service.AnimalTransferService;
import zoo.service.FeedingOrganizationService;
import zoo.service.ZooStatisticService;
import zoo.web.dto.requests.AnimalFoodRequest;

/**
 * Контроллер для обработки запросов, связанных со статистикой.
 */
@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
@Tag(name = "Статистика", description = "Вычисление статистики")
@Service
public class StatisticController {
    private final FeedingOrganizationService feedingOrganizationService;

    private final AnimalTransferService animalTransferService;

    private final ZooStatisticService zooStatisticService;

    /**
     * Возвращает отчёт о перемещениях животных.
     */
    @GetMapping("/report/transfer")
    @Operation(summary = "Отчёт о передвижениях")
    public ResponseEntity<Report> transferReport() {
        return ResponseEntity.ok(animalTransferService.getReport());
    }

    /**
     * Возвращает отчёт о перемещениях животных.
     */
    @GetMapping("/report/feed")
    @Operation(summary = "Отчёт о кормлении")
    public ResponseEntity<Report> feedingReport() {
        return ResponseEntity.ok(feedingOrganizationService.getReport());
    }

    /**
     * Возвращает число животных с выбранной любимой едой.
     */
    @PostMapping("/favorite_food")
    @Operation(summary = "Кол-во любителей", description = "Возвращает число животных с выбранной любимой едой")
    public ResponseEntity<Integer> countAnimalsWithFavoriteFood(@Valid @RequestBody AnimalFoodRequest request,
                                                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ZooException(bindingResult.getAllErrors().getFirst().getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(zooStatisticService.countAnimalsWithFavoriteFood(request.foodName()));
    }

    /**
     * Возвращает число животных, которых кормят выбранной едой.
     */
    @PostMapping("/feed_count")
    @Operation(summary = "Кол-во получателей", description = "Возвращает число животных, которых кормят выбранной едой")
    public ResponseEntity<Integer> countAnimalsFedWithFood(@Valid @RequestBody AnimalFoodRequest request,
                                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ZooException(bindingResult.getAllErrors().getFirst().getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(zooStatisticService.countAnimalsFedWithFood(request.foodName()));
    }
}