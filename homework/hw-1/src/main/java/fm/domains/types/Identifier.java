package fm.domains.types;

import fm.exceptions.WrongValueException;
import java.util.Objects;
import lombok.Getter;

/**
 * Класс, представляющий значения идентификатора.
 */
@Getter
public class Identifier {
    private int id;

    public static final int MIN_ID = 1;

    public static final int MAX_ID = 1_000_000_000;

    /**
     * Setter для идентификатора.
     *
     * @param newId новое значение
     */
    public void setId(int newId) {
        if (newId <= 0) {
            throw new WrongValueException("Id must be greater than 0");
        }
        id = newId;
    }

    public Identifier(int id) {
        setId(id);
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        return id == ((Identifier) other).id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
