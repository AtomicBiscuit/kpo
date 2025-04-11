package hse.kpo.controllers.catamarans;

import hse.kpo.domains.*;
import hse.kpo.dto.request.CatamaranRequest;
import hse.kpo.enums.EngineTypes;
import hse.kpo.facade.Hse;
import hse.kpo.services.catamarans.HseCatamaranService;
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
    private final HseCatamaranService catamaranService;

    private final Hse hseFacade;

    @GetMapping("/{vin}")
    @Operation(summary = "Получить катамаран по VIN")
    public ResponseEntity<Catamaran> getCatamaranByVin(@PathVariable int vin) {
        return catamaranService.getCatamarans()
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

    @DeleteMapping("/{vin}")
    @Operation(summary = "Удалить катамаран")
    public ResponseEntity<Void> deleteCatamaran(@PathVariable int vin) {
        boolean removed = catamaranService.getCatamarans().removeIf(catamaran -> catamaran.getVin() == vin);
        return removed ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @GetMapping
    @Operation(summary = "Получить все катамараны с фильтрацией", parameters = {
            @Parameter(name = "engineType", description = "Фильтр по типу двигателя"),
            @Parameter(name = "minVin", description = "Минимальный VIN")
    })
    public List<Catamaran> getAllCatamarans(@RequestParam(required = false) String engineType,
                                            @RequestParam(required = false) Integer minVin) {
        return catamaranService.getCatamarans()
                               .stream()
                               .filter(catamaran -> engineType == null || catamaran.getEngineType().equals(engineType))
                               .filter(catamaran -> minVin == null || catamaran.getVin() >= minVin)
                               .toList();
    }

    private Catamaran createCatamaranFromRequest(CatamaranRequest request, int vin) {
        AbstractEngine engine = switch (EngineTypes.valueOf(request.engineType())) {
            case PEDAL -> new PedalEngine(request.pedalSize());
            case HAND -> new HandEngine();
            case LEVITATION -> new LevitationEngine();
        };
        return new Catamaran(vin, engine);
    }
}