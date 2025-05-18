package zoo.web.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
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
import zoo.domain.enclosures.Enclosure;
import zoo.domain.enclosures.EnclosureFactory;
import zoo.domain.enclosures.EnclosureProvider;
import zoo.domain.exceptions.ZooException;
import zoo.web.dto.requests.EnclosureCreateRequest;
import zoo.web.dto.requests.EnclosureUpdateRequest;
import zoo.web.dto.responses.EnclosureResponse;

/**
 * Контроллер для обработки запросов, связанных с вольерами.
 */
@RestController
@RequestMapping("/api/enclosure")
@RequiredArgsConstructor
@Tag(name = "Вольеры", description = "Управление вольерами")
@Service
public class EnclosureController {
    private final EnclosureProvider enclosureProvider;

    private final EnclosureFactory enclosureFactory;

    /**
     * Возвращает вольер по Id.
     *
     * @param id идентификатор
     * @return Представление в виде HttpResponse {@link Enclosure}
     */
    @GetMapping("/{id}")
    public ResponseEntity<EnclosureResponse> getById(@PathVariable int id) {
        var enclosure = enclosureProvider.getEnclosureById(id);
        if (enclosure == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(EnclosureResponse.from(enclosure));
    }

    /**
     * Возвращает список из всех вольеров.
     *
     * @return Представление в виде HttpResponse {@link Enclosure List of Enclosure}
     */
    @GetMapping("/all")
    public ResponseEntity<List<EnclosureResponse>> getAll() {
        var enclosures = enclosureProvider.getEnclosures();
        if (enclosures == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(enclosures.stream().map(EnclosureResponse::from).toList());
    }


    /**
     * Обработка запроса на создание вольера.
     *
     * @param request {@link EnclosureCreateRequest}
     * @return Представление в виде HttpResponse {@link Enclosure}
     */
    @PostMapping("/create")
    @Operation(summary = "Добавить вольер")
    public ResponseEntity<EnclosureResponse> create(@Valid @RequestBody EnclosureCreateRequest request,
                                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ZooException(bindingResult.getAllErrors().getFirst().getDefaultMessage());
        }

        var enclosure = enclosureProvider.save(enclosureFactory.createEnclosure(
                request.type(),
                request.size(),
                request.capacity()
        ));

        return ResponseEntity.status(HttpStatus.CREATED).body(EnclosureResponse.from(enclosure));
    }

    /**
     * Обработка запроса на изменение данных о вольере.
     *
     * @param request {@link EnclosureUpdateRequest}
     * @return Представление в виде HttpResponse {@link Enclosure}
     */
    @PostMapping("/update")
    @Operation(summary = "Изменить данные вольера",
            description = "Оставляйте поля пустыми, чтобы сохранить старое значение")
    public ResponseEntity<EnclosureResponse> update(@Valid @RequestBody EnclosureUpdateRequest request,
                                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ZooException(bindingResult.getAllErrors().getFirst().getDefaultMessage());
        }

        var enclosure = enclosureProvider.getEnclosureById(request.id());

        if (Objects.isNull(enclosure)) {
            return ResponseEntity.notFound().build();
        }

        if (Objects.nonNull(request.type())) {
            enclosure.setType(request.type());
        }

        if (Objects.nonNull(request.size())) {
            enclosure.setSize(request.size());
        }

        if (Objects.nonNull(request.capacity())) {
            enclosure.setCapacity(request.capacity());
        }

        return ResponseEntity.ok(EnclosureResponse.from(enclosureProvider.save(enclosure)));
    }

    /**
     * Удаляет вольер по Id.
     *
     * @param id идентификатор
     * @return HttpStatus Ok
     */
    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Удалить вольер")
    public ResponseEntity<Void> removeById(@PathVariable int id) {
        var enclosure = enclosureProvider.getEnclosureById(id);
        if (enclosure == null) {
            return ResponseEntity.ok().build();
        }

        if (enclosure.getAnimalsCount() > 0) {
            throw new ZooException("Can`t remove enclosure with attached animals");
        }
        enclosureProvider.deleteById(id);
        return ResponseEntity.ok().build();
    }
}