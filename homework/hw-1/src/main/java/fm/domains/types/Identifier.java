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

    /**
     * Увеличивает значение идентификатора на number
     *
     * @param number значение, которое будет добавлено к id
     */
    public void increase(int number) {
        if (number < 0) {
            throw new WrongValueException("Increasing value must be not less than 0");
        }
        id += number;
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
