package zoo.service.observers;

import zoo.domain.reports.Report;

/**
 * Базовый интерфейс для наблюдателей.
 */
public interface Observer<T> {
    void onAction(T object);

    Report buildReport();
}