package zoo.service;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import zoo.domain.animals.Animal;
import zoo.domain.enclosures.Enclosure;
import zoo.domain.enclosures.EnclosureProvider;
import zoo.domain.exceptions.ZooException;
import zoo.domain.reports.Report;
import zoo.repository.AnimalRepository;
import zoo.service.observers.AnimalMoveObserver;

/**
 * Сервис для перевода животных между сервисами.
 */
@Service
@AllArgsConstructor
public class AnimalTransferService {
    private final AnimalRepository animalRepository;

    private final EnclosureProvider enclosureProvider;

    private final AnimalMoveObserver observer;

    /**
     * Перемещает животное в новый вольер.
     *
     * @param animal       животное, которое будет перемещено
     * @param newEnclosure вольер, в который будет переведено животное
     */
    public Animal moveAnimal(@NotNull Animal animal, Enclosure newEnclosure) {
        var oldEnclosure = animal.getEnclosure();

        if (Objects.nonNull(oldEnclosure)) {
            oldEnclosure.removeAnimal();
        }

        if (Objects.nonNull(newEnclosure)) {
            newEnclosure.addAnimal();
        }

        animal.moveTo(newEnclosure);

        if (Objects.nonNull(oldEnclosure)) {
            enclosureProvider.save(oldEnclosure);
        }
        if (Objects.nonNull(newEnclosure)) {
            enclosureProvider.save(newEnclosure);
        }
        var savedAnimal = animalRepository.save(animal);

        observer.onAction(savedAnimal);
        return savedAnimal;
    }

    /**
     * Перемещает животное в новый вольер по id.
     *
     * @param animalId       id животного, которое будет перемещено
     * @param newEnclosureId id вольера, в который будет перемещено животное
     */
    @Transactional
    public Animal moveAnimalById(@NotNull Integer animalId, Integer newEnclosureId) {
        var animal = animalRepository.getAnimalById(animalId);
        var newEnclosure = enclosureProvider.getEnclosureById(newEnclosureId);

        if (animal == null) {
            throw new ZooException("Can`t find animal with id: " + animalId);
        }

        if (Objects.nonNull(newEnclosureId) && newEnclosure == null) {
            throw new ZooException("Can`t find enclosure with id: " + newEnclosureId);
        }

        return moveAnimal(animal, newEnclosure);
    }

    /**
     * Генерирует отчёт о перемещении животных.
     *
     * @return {@link Report}
     */
    public Report getReport() {
        return observer.buildReport();
    }
}
