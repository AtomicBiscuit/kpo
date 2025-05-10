package fm.exceptions;

/**
 * Исключение, означающее некорректное значение для задания типа.
 */
public class IdentifyNotFoundException extends RuntimeException {
    public IdentifyNotFoundException(String message) {
        super(message);
    }
}
