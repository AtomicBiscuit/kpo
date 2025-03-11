package fm.exceptions;

/**
 * Исключение, означающее некорректное значение для задания типа.
 */
public class WrongValueException extends RuntimeException {
    public WrongValueException(String message) {
        super(message);
    }
}
