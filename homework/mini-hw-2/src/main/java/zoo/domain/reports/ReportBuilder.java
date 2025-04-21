package zoo.domain.reports;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Класс для составления отчета о работе системы.
 */
public class ReportBuilder {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final StringBuilder content = new StringBuilder();

    /**
     * Добавление информации о действиях в системе в отчет.
     *
     * @param operation операция в системе
     * @param text      содержание операции
     * @return {@link ReportBuilder} для дальнейшего составления отчета
     */
    public ReportBuilder addOperation(String operation, String text) {
        content.append(String.format("%s: %s", operation, text));
        content.append(System.lineSeparator());
        return this;
    }

    /**
     * Получение итогового отчета о системе.
     *
     * @return {@link Report} отчет о системе
     */
    public Report build() {
        return new Report(
                String.format("Отчет за %s", ZonedDateTime.now().format(DATE_TIME_FORMATTER)),
                content.toString()
        );
    }
}