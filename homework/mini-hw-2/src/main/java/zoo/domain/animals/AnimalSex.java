package zoo.domain.animals;

import java.util.Optional;
import lombok.Getter;

/**
 * Список полов животных.
 */
public enum AnimalSex {
    MALE("male"), FEMALE("female"), HERMAPHRODITE("hermaphrodite");

    @Getter
    final String name;

    AnimalSex(String label) {
        name = label;
    }

    /**
     * Возвращает пол по строке.
     *
     * @param label текстовое представление пола
     * @return пол
     */
    public static Optional<AnimalSex> fromString(String label) {
        try {
            return Optional.of(AnimalSex.valueOf(label.toUpperCase()));
        } catch (IllegalArgumentException ignored) {
            return Optional.empty();
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
