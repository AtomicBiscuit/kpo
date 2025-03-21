package hse.kpo.api.controllers;

import hse.kpo.api.requests.CatamaranRequest;
import hse.kpo.domains.Catamaran;
import hse.kpo.domains.HandEngine;
import hse.kpo.domains.LevitationEngine;
import hse.kpo.domains.PedalEngine;
import hse.kpo.enums.EngineTypes;
import hse.kpo.facade.Hse;
import hse.kpo.interfaces.Engine;
import hse.kpo.services.catamarans.HseCatamaranService;
import hse.kpo.storages.CatamaranStorage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/catamarans")
@RequiredArgsConstructor
@Tag(name = "Катамараны", description = "Управление транспортными средствами")
public class CatamaranController {
    private final CatamaranStorage catamaranStorage;

    private final HseCatamaranService catamaranService;

    private final Hse hseFacade;

    @GetMapping("/{vin}")
    @Operation(summary = "Получить катамаран по VIN")
    public ResponseEntity<Catamaran> getCatamaranByVin(@PathVariable int vin) {
        return catamaranStorage.getCatamarans()
                               .stream()
                               .filter(catamaran -> catamaran.getVin() == vin)
                               .findFirst()
                               .map(ResponseEntity::ok)
                               .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Создать катамаран", description = "Для PEDAL требуется pedalSize (1-15)")
    public ResponseEntity<Catamaran> createCatamaran(@Valid @RequestBody CatamaranRequest request,
                                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    bindingResult.getAllErrors().getFirst().getDefaultMessage()
            );
        }

        var engineType = EngineTypes.find(request.engineType());
        if (engineType.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown engine type");
        }

        var catamaran = switch (engineType.get()) {
            case EngineTypes.PEDAL -> hseFacade.addPedalCatamaran(request.pedalSize());
            case EngineTypes.HAND -> hseFacade.addHandCatamaran();
            case EngineTypes.LEVITATION -> hseFacade.addLevitationCatamaran();
            default -> throw new RuntimeException();
        };

        return ResponseEntity.status(HttpStatus.CREATED).body(catamaran);
    }

    @PostMapping("/sell")
    @Operation(summary = "Продать все доступные катамараны")
    public ResponseEntity<Void> sellAllCatamarans() {
        catamaranService.sellCatamarans();
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{vin}")
    @Operation(summary = "Обновить катамаран")
    public ResponseEntity<Catamaran> updateCatamaran(@PathVariable int vin,
                                                     @Valid @RequestBody CatamaranRequest request) {

        var update = createCatamaranFromRequest(request, vin);

        var optionalCatamaran = catamaranStorage.getCatamarans()
                                                .stream()
                                                .filter(catamaran -> catamaran.getVin() == vin)
                                                .findFirst();
        if (optionalCatamaran.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var catamaran = optionalCatamaran.get();
        catamaran.setEngine(update.getEngine());
        return ResponseEntity.ok(catamaran);
    }

    @DeleteMapping("/{vin}")
    @Operation(summary = "Удалить катамаран")
    public ResponseEntity<Void> deleteCatamaran(@PathVariable int vin) {
        boolean removed = catamaranStorage.getCatamarans().removeIf(catamaran -> catamaran.getVin() == vin);
        return removed ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @GetMapping
    @Operation(summary = "Получить все катамараны с фильтрацией", parameters = {
            @Parameter(name = "engineType", description = "Фильтр по типу двигателя"),
            @Parameter(name = "minVin", description = "Минимальный VIN")
    })
    public List<Catamaran> getAllCatamarans(@RequestParam(required = false) String engineType,
                                            @RequestParam(required = false) Integer minVin) {
        return catamaranStorage.getCatamarans()
                               .stream()
                               .filter(catamaran -> engineType == null || catamaran.getEngineType().equals(engineType))
                               .filter(catamaran -> minVin == null || catamaran.getVin() >= minVin)
                               .toList();
    }

    private Catamaran createCatamaranFromRequest(CatamaranRequest request, int vin) {
        Engine engine = switch (EngineTypes.valueOf(request.engineType())) {
            case PEDAL -> new PedalEngine(request.pedalSize());
            case HAND -> new HandEngine();
            case LEVITATION -> new LevitationEngine();
        };
        return new Catamaran(vin, engine);
    }
}