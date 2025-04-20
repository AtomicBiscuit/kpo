package zoo.service.observers;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.stereotype.Component;
import zoo.domain.animals.Animal;
import zoo.domain.reports.Report;
import zoo.domain.reports.ReportBuilder;

/**
 * Наблюдатель за перемещением животных.
 */
@Component
@RequiredArgsConstructor
public class AnimalMoveObserver implements Observer<Animal> {
    private final ReportBuilder reportBuilder = new ReportBuilder();

    @Override
    @Synchronized
    public void onAction(Animal animal) {
        if (Objects.isNull(animal)) {
            return;
        }
        String message = String.format(
                "%s с ID-%d в вольер ID-%d",
                animal.getName(),
                animal.getId(),
                animal.getEnclosure() != null ? animal.getEnclosure().getId() : null
        );
        reportBuilder.addOperation("Перемещение", message);
    }

    @Override
    @Synchronized
    public Report buildReport() {
        return reportBuilder.build();
    }
}