package zoo.domain.reports;

/**
 * Отчет о работе системы.
 *
 * @param title   название отчета
 * @param content наполнение отчета
 */
public record Report(
        String title,

        String content
) {}